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
package es.nivel36.laie.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FileDtoMapperTest extends FileTest {

	@Nested
	class Map {

		@Test
		public void FileShouldReturnFileDto() {
			final File file = mockFile();

			final FileMapper fm = new FileMapper();
			final FileDto fileDto = fm.map(file);

			Assertions.assertAll( // Assert all even if one fails.
					() -> Assertions.assertEquals(file.getName(), fileDto.getName()),
					() -> Assertions.assertEquals(file.getDescription(), fileDto.getDescription()),
					() -> Assertions.assertEquals(file.getUid(), fileDto.getUid()),
					() -> Assertions.assertEquals(file.getCreated(), fileDto.getCreated()));
		}

		@Test
		public void NullFileShouldReturnNullFileDto() {
			final FileMapper fm = new FileMapper();
			final FileDto file = fm.map(null);

			Assertions.assertNull(file);
		}
	}
}
