/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.core.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator of a random hexadecimal string. This generator is capable of
 * generating variable length strings (up to a maximum of 128 characters).
 * 
 * @author Abel Ferrer
 *
 */
public final class UidGenerator {

	private static final String _02X = "%02X";

	private final byte[] bytes;

	private final Random random;

	private final int size;

	/**
	 * The constructor of the class. It receives as parameter the number of
	 * hexadecimal characters that the random string will have.
	 * 
	 * @param size <tt>int</tt> with the size of the random string.
	 */
	public UidGenerator(final int size) {
		if ((size < 1) || (size > 128)) {
			throw new IllegalArgumentException(String.format("Size %d is out of limits", size));
		}
		this.size = size;
		this.random = ThreadLocalRandom.current();
		final int byteSize = (size % 2) == 0 ? size / 2 : (size + 1) / 2;
		this.bytes = new byte[byteSize];
	}

	/**
	 * Generates a hexadecimal string of random characters with the length
	 * determined by the constructor.
	 * 
	 * @return <tt>String</tt> with a random hexadecimal value.
	 */
	public String generate() {
		this.random.nextBytes(this.bytes);

		final StringBuilder sb = new StringBuilder();
		for (final byte b : this.bytes) {
			sb.append(String.format(_02X, b));
		}

		final String randomString;
		if ((this.size % 2) == 0) {
			randomString = sb.toString();
		} else {
			randomString = sb.substring(0, this.size);
		}
		return randomString;
	}
}
