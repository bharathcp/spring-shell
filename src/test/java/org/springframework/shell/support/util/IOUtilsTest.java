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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;
import org.springframework.shell.support.util.IOUtils;

/**
 * Unit test of {@link IOUtils}.
 *
 * @author Andrew Swan
 * @since 1.2.0
 */
public class IOUtilsTest {

	@Test
	public void testCloseNullCloseable() {
		IOUtils.closeQuietly((Closeable) null); // Shouldn't throw an exception
	}

	@Test
	public void testCloseNonNullCloseableWithoutError() throws Exception {
		// Set up
		final Closeable mockCloseable = mock(Closeable.class);

		// Invoke
		IOUtils.closeQuietly(mockCloseable);

		// Check
		verify(mockCloseable).close();
	}

	@Test
	public void testCloseTwoNonNullCloseableWithErrorOnFirst() throws Exception {
		// Set up
		final Closeable mockCloseable1 = mock(Closeable.class);
		doThrow(new IOException("dummy")).when(mockCloseable1).close();
		final Closeable mockCloseable2 = mock(Closeable.class);

		// Invoke
		IOUtils.closeQuietly(mockCloseable1, mockCloseable2);

		// Check
		verify(mockCloseable1).close();
		verify(mockCloseable2).close();
	}
}
