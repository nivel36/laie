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

abstract class AbstractUserTest {

	protected User mockUser(final String uid) {
		return mockUser(uid, null);
	}

	protected User mockUser(final String uid, final User manager) {
		return mockUser(uid, manager, uid + "@email.com");
	}

	protected User mockUser(final String uid, final User manager, final String email) {
		final User user = new User();
		user.setEmail(email);
		user.setId(1l);
		user.setIdNumber(uid);
		user.setName("Name" + uid);
		user.setPhoneNumber("1234567890");
		user.setRole(Role.USER);
		user.setSurname("surname" + uid);
		user.setJobPosition("Developer");
		user.setUid(uid);
		user.setVersion(1l);
		user.setManager(manager);
		return user;
	}
	
	protected UserDto mockUserDto(final String uid) {
		return mockUserDto(uid, null);
	}

	protected UserDto mockUserDto(final String uid, final SimpleUserDto manager) {
		return mockUserDto(uid, manager, uid + "@email.com");
	}

	protected UserDto mockUserDto(final String uid, SimpleUserDto manager, String email) {
		final UserDto userDto = new UserDto();
		userDto.setEmail(email);
		userDto.setIdNumber(uid);
		userDto.setManager(manager);
		userDto.setName("Name" + uid);
		userDto.setJobPosition("Developer");
		userDto.setPhoneNumber("1234567890");
		userDto.setRole(Role.USER);
		userDto.setSurname("surname" + uid);
		userDto.setUid(uid);
		return userDto;
	}

	protected SimpleUserDto mockSimpleUserDto(final String uid) {
		return new SimpleUserDto(uid, "Name" + uid, null);
	}
}
