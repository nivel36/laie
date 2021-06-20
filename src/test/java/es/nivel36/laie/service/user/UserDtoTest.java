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
package es.nivel36.laie.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserDtoTest {
	
	@Nested
	class Equals {

		@Test
		public void nullUserShouldReturnFalse() {
			final UserDto user = new UserDto();
			final boolean equals = user.equals(null);
			Assertions.assertFalse(equals);
		}

		@Test
		public void differentClassShouldReturnFalse() {
			final UserDto user = new UserDto();
			final boolean equals = user.equals(new User());
			Assertions.assertFalse(equals);
		}
		
		@Test
		public void equalUserMustReturnTrue() {
			final UserDto user = new UserDto();
			final boolean equals = user.equals(user);
			Assertions.assertTrue(equals);
		}
		
		@Test
		public void usersWithEqualsUidMustReturnTrue() {
			final UserDto user1 = new UserDto();
			user1.setUid("krej");
			final UserDto user2 = new UserDto();
			user2.setUid("krej");
			final boolean equals = user1.equals(user2);
			Assertions.assertTrue(equals);
		}
		
		@Test
		public void userWithNonEqualUidMustReturnFalse() {
			final UserDto user1 = new UserDto();
			user1.setUid("krej");
			final UserDto user2 = new UserDto();
			user2.setUid("afej");
			final boolean equals = user1.equals(user2);
			Assertions.assertFalse(equals);
		}
	}
	
	@Nested
	class ToString {

		@Test
		public void shouldReturnNameAndSurname() {
			final UserDto user = new UserDto();
			user.setName("Abel");
			user.setSurname("Ferrer");
			final String toString = user.toString();
			Assertions.assertEquals("Abel Ferrer", toString);
		}
	}

}
