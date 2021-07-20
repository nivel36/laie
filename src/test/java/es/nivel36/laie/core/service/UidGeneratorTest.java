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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UidGeneratorTest {

	@Nested
	class UidGeneratorConstructor {

		@Test
		public void badLowSizeShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				new RandomGenerator(-1);
			});
		}
		
		@Test
		public void badHighSizeShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				new RandomGenerator(129);
			});
		}
	}

	@Nested
	class Generate {

		@Test
		public void pairLengthShouldReturnHexRandomValue() {
			final int length = 6;
			final RandomGenerator uidGenerator = new RandomGenerator(length);
			String random = uidGenerator.generateHex();
			Assertions.assertEquals(length, random.length());
			Long.parseLong(random, 16); // validate hexValue
		}

		@Test
		public void oddLengthSforShouldReturnHexRandomValue() {
			final int length = 5;
			final RandomGenerator uidGenerator = new RandomGenerator(length);
			String random = uidGenerator.generateHex();
			Assertions.assertEquals(length, random.length());
			Long.parseLong(random, 16); // validate hexValue
		}
	}
}
