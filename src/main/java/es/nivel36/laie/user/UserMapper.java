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
import es.nivel36.laie.department.Department;
import es.nivel36.laie.department.SimpleDepartmentDto;
import es.nivel36.laie.file.File;
import es.nivel36.laie.file.FileMapper;

/**
 * A mapper for the class UserDto
 *
 * @author Abel Ferrer
 *
 * @see User
 * @see UserDto
 */
public class UserMapper extends AbstractMapper<User, UserDto> {

	private final SimpleUserMapper simpleUserMapper;
	
	private final FileMapper fileMapper;

	public UserMapper() {
		this.simpleUserMapper = new SimpleUserMapper();
		this.fileMapper = new FileMapper();
	}

	/**
	 * Maps the object passed as a parameter in the constructor to an object of the
	 * UserDto class.
	 *
	 * @return <tt>UserDto</tt> with all variables equal to those of the
	 *         <tt>User</tt> object passed as a parameter in the constructor.
	 */
	@Override
	public UserDto map(final User user) {
		if (user == null) {
			return null;
		}
		final UserDto userDto = new UserDto();
		final Department department = user.getDepartment();
		if (department != null) {
			final String uid = department.getUid();
			final String name = department.getName();
			final SimpleDepartmentDto simpleDepartment = new SimpleDepartmentDto(uid, name);
			userDto.setDepartment(simpleDepartment);
		}
		userDto.setEmail(user.getEmail());
		userDto.setIdNumber(user.getIdNumber());
		final File image = user.getImage();	
		userDto.setImage(fileMapper.map(image));
		userDto.setJobPosition(user.getJobPosition());
		userDto.setLocale(user.getLocale());
		final User manager = user.getManager();
		final SimpleUserDto simpleManagerDto = simpleUserMapper.map(manager);
		userDto.setManager(simpleManagerDto);
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		userDto.setSurname(user.getSurname());
		userDto.setUid(user.getUid());
		return userDto;
	}
}
