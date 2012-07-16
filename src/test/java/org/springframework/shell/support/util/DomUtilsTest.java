/*
 * Copyright 2011-2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.shell.support.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.shell.support.util.DomUtils;
import org.springframework.shell.support.util.StringUtils;
import org.springframework.shell.support.util.XmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Unit test of {@link DomUtils}
 *
 * @author Andrew Swan
 * @since 1.2.0
 */
public class DomUtilsTest {

	// Constants
	private static final String DEFAULT_TEXT = "foo";
	private static final String NODE_TEXT = "bar";
	private static final String XML_BEFORE_REMOVAL =
		"<top>" +
		"    <middle>" +
		"        <bottom id=\"1\" />" +
		"        <bottom id=\"2\" />" +
		"    </middle>" +
		"</top>";
	private static final String XML_AFTER_REMOVAL =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<top>    <middle/>\n" +
		"</top>";
	
	/**
	 * Asserts that the given XML node contains the expected content
	 *
	 * @param expectedLines the expected lines of XML (required); separate each
	 * line with "\n" regardless of the platform
	 * @param actualNode the actual XML node (required)
	 * @throws AssertionError if they are not equal
	 */
	private void assertXmlEquals(final String expectedXml, final Node actualNode) {
		// Replace the dummy line terminator with the platform-specific one that
		// will be applied by XmlUtils.nodeToString.
		final String normalisedXml = expectedXml.replace("\n", StringUtils.LINE_SEPARATOR);
		// Trim trailing whitespace as XmlUtils.nodeToString appends an extra newline.
		final String actualXml = StringUtils.trimTrailingWhitespace(XmlUtils.nodeToString(actualNode));
		assertEquals(normalisedXml, actualXml);
	}

	@Test
	public void testGetTextContentOfNullNode() {
		assertEquals(DEFAULT_TEXT, DomUtils.getTextContent(null, DEFAULT_TEXT));
	}

	@Test
	public void testGetTextContentOfNonNullNode() {
		// Set up
		final Node mockNode = mock(Node.class);
		when(mockNode.getTextContent()).thenReturn(NODE_TEXT);

		assertEquals(NODE_TEXT, DomUtils.getTextContent(mockNode, DEFAULT_TEXT));
	}
	
	@Test
	public void testRemoveElements() throws Exception {
		// Set up
		final Element root = XmlUtils.stringToElement(XML_BEFORE_REMOVAL);
		final Element middle = DomUtils.getChildElementByTagName(root, "middle");
		
		// Invoke
		DomUtils.removeElements("bottom", middle);
		
		// Check
		assertXmlEquals(XML_AFTER_REMOVAL, root);
	}
}
