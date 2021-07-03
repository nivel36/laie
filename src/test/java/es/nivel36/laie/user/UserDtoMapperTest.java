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
package es.nivel36.laie.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserDtoMapperTest extends AbstractUserTest {

	@Nested
	class Map {

		@Test
		public void UserShouldReturnUserDto() {
			final User user = mockUser("krej", mockUser("afej", null));

			final UserMapper mapper = new UserMapper();
			final UserDto userDto = mapper.map(user);

			Assertions.assertAll( // Assert all even if one fails.
					() -> Assertions.assertEquals(user.getEmail(), userDto.getEmail()),
					() -> Assertions.assertEquals(user.getIdNumber(), userDto.getIdNumber()),
					() -> Assertions.assertEquals(user.getLocale(), userDto.getLocale()),
					() -> Assertions.assertEquals(user.getManager().toString(), userDto.getManager().getFullName()),
					() -> Assertions.assertEquals(user.getManager().getUid(), userDto.getManager().getUid()),
					() -> Assertions.assertEquals(user.getName(), userDto.getName()),
					() -> Assertions.assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber()),
					() -> Assertions.assertEquals(user.getRole(), userDto.getRole()),
					() -> Assertions.assertEquals(user.getSurname(), userDto.getSurname()),
					() -> Assertions.assertEquals(user.getUid(), userDto.getUid()));
		}

		@Test
		public void NullUserShouldReturnNullUserDto() {
			final UserMapper mapper = new UserMapper();
			final UserDto userDto = mapper.map(null);

			Assertions.assertNull(userDto);
		}
	}
}
