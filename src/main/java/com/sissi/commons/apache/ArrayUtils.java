/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sissi.commons.apache;

/**
 * <p>
 * Operations on arrays, primitive arrays (like <code>int[]</code>) and primitive wrapper arrays (like <code>Integer[]</code>).
 * </p>
 * 
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception will not be thrown for a <code>null</code> array input. However, an Object array that contains a <code>null</code> element may throw an exception. Each method documents its behaviour.
 * </p>
 * 
 * <p>
 * #ThreadSafe#
 * </p>
 * 
 * @author Apache Software Foundation
 * @author Moritz Petersen
 * @author <a href="mailto:fredrik@westermarck.com">Fredrik Westermarck</a>
 * @author Nikolay Metchev
 * @author Matthew Hawthorne
 * @author Tim O'Brien
 * @author Pete Gieser
 * @author Gary Gregory
 * @author <a href="mailto:equinus100@hotmail.com">Ashwin S</a>
 * @author Maarten Coene
 * @since 2.0
 * @version $Id: ArrayUtils.java 1056988 2011-01-09 17:58:53Z niallp $
 */
public class ArrayUtils {

	/**
	 * An empty immutable <code>int</code> array.
	 */
	public final static int[] EMPTY_INT_ARRAY = new int[0];

	/**
	 * The index value when an element is not found in a list or array: <code>-1</code>. This value is returned by methods in this class and can also be used in comparisons with values returned by various method from {@link java.util.List}.
	 */
	public final static int INDEX_NOT_FOUND = -1;

	public static int[] toPrimitive(Integer[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_INT_ARRAY;
		}
		final int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].intValue();
		}
		return result;
	}

	public static int lastIndexOf(byte[] array, byte valueToFind) {
		return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
	}

	public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
		if (array == null) {
			return INDEX_NOT_FOUND;
		}
		if (startIndex < 0) {
			return INDEX_NOT_FOUND;
		} else if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}
}
