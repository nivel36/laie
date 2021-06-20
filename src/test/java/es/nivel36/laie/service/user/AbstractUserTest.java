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

abstract class AbstractUserTest {

	protected User mockUser(final String uid, final User manager) {
		final User user = new User();
		user.setEmail(uid + "@email.com");
		user.setId(1l);
		user.setIdNumber(uid);
		user.setName("Name" + uid);
		user.setPhoneNumber("1234567890");
		user.setRole(Role.USER);
		user.setSurname("surname" + uid);
		user.setUid(uid);
		user.setVersion(1l);
		user.setManager(manager);
		return user;
	}

	protected UserDto mockUserDto(final String uid, final String managerUid, final String managerFullName) {
		final UserDto userDto = new UserDto();
		userDto.setEmail(uid + "@email.com");
		userDto.setIdNumber(uid);
		userDto.setManagerFullName(managerFullName);
		userDto.setManagerUid(managerUid);
		userDto.setName("Name" + uid);
		userDto.setPhoneNumber("1234567890");
		userDto.setRole(Role.USER);
		userDto.setSurname("surname" + uid);
		userDto.setUid(uid);
		return userDto;
	}
}
