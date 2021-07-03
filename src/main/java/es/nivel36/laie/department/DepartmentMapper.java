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
package es.nivel36.laie.department;

import java.util.Set;

import es.nivel36.laie.core.AbstractMapper;
import es.nivel36.laie.user.SimpleUserDto;
import es.nivel36.laie.user.SimpleUserMapper;
import es.nivel36.laie.user.User;

/**
 * A mapper for the class DepartmentDto
 *
 * @author Abel Ferrer
 *
 * @see Department
 * @see DepartmentDto
 */
public class DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {

	private final SimpleUserMapper simpleUserMapper;

	public DepartmentMapper() {
		this.simpleUserMapper = new SimpleUserMapper();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentDto map(final Department department) {
		if (department == null) {
			return null;
		}
		final DepartmentDto departmentDto = new DepartmentDto();
		final User head = department.getHead();
		final SimpleUserDto headDto = this.simpleUserMapper.map(head);
		departmentDto.setHead(headDto);
		departmentDto.setName(department.getName());
		departmentDto.setUid(department.getUid());
		final Set<User> workers = department.getWorkers();
		final Set<SimpleUserDto> workerDtos = this.simpleUserMapper.mapSet(workers);
		departmentDto.setWorkers(workerDtos);
		return departmentDto;
	}
}
