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

/**
 * A mapper for the class UserDto
 * 
 * @author Abel Ferrer
 *
 *@see User
 *@see UserDto
 */
public class UserDtoMapper {

	private final User user;

	/**
	 * Simple constructor.
	 * 
	 * @param user <tt>User</tt> to map. This parameter can be null.
	 */
	public UserDtoMapper(final User user) {
		this.user = user;
	}

	/**
	 * Maps the object passed as a parameter in the constructor to an object of the
	 * UserDto class.
	 * 
	 * @return <tt>UserDto</tt> with all variables equal to those of the <tt>User</tt> object
	 *         passed as a parameter in the constructor.
	 */
	public UserDto map() {
		if (this.user == null) {
			return null;
		}
		final UserDto userDto = new UserDto();
		userDto.setEmail(this.user.getEmail());
		userDto.setIdNumber(this.user.getIdNumber());
		final User manager = this.user.getManager();
		if (manager != null) {
			userDto.setManagerFullName(manager.toString());
			userDto.setManagerUid(manager.getUid());
		}
		userDto.setName(this.user.getName());
		userDto.setPhoneNumber(this.user.getPhoneNumber());
		userDto.setRole(this.user.getRole());
		userDto.setSurname(this.user.getSurname());
		userDto.setUid(this.user.getUid());
		return userDto;
	}
}
