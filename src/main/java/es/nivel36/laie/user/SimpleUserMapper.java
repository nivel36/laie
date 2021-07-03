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

import es.nivel36.laie.core.AbstractMapper;
import es.nivel36.laie.file.File;

/**
 * A mapper for the class SimpleUserDto
 *
 * @author Abel Ferrer
 *
 * @see User
 * @see UserDto
 */
public class SimpleUserMapper extends AbstractMapper<User, SimpleUserDto> {

	/**
	 * Maps the object passed as a parameter in the constructor to an object of the
	 * SimpleUserDto class.
	 *
	 * @return <tt>SimpleUserDto</tt> with all variables equal to those of the
	 *         <tt>User</tt> object passed as a parameter in the constructor.
	 */
	@Override
	public SimpleUserDto map(final User user) {
		if (user == null) {
			return null;
		}
		final SimpleUserDto dto = new SimpleUserDto();
		dto.setFullName(user.toString());
		dto.setUid(user.getUid());
		final File image = user.getImage();
		if (image != null) {
			dto.setImage(image.getPath());
		}
		return dto;
	}
}
