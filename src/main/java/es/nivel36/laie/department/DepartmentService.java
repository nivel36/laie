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

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.AbstractService;
import es.nivel36.laie.core.service.IdentifiableNotFoundException;
import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.user.SimpleUserDto;
import es.nivel36.laie.user.User;
import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserJpaDao;
import es.nivel36.laie.user.UserMapper;
import es.nivel36.laie.user.UserService;

@Stateless
public class DepartmentService extends AbstractService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@Repository
	@Inject
	private DepartmentJpaDao departmentDao;

	private final DepartmentMapper departmentMapper;

	@Repository
	@Inject
	private UserJpaDao userDao;

	private final UserMapper userMapper;

	////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Simple constructor.
	 */
	public DepartmentService() {
		this.userMapper = new UserMapper();
		this.departmentMapper = new DepartmentMapper();
	}

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Searches for all departments.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 100.
	 */
	public List<DepartmentDto> findAll(final int page, final int pageSize) {
		validatePagination(page, pageSize);
		logger.debug("Find all departments, page {}, page size", page, pageSize);
		final List<Department> departments = this.departmentDao.findAll(page, pageSize);
		return departmentMapper.mapList(departments);
	}

	/**
	 * Searches for all users in the department that is passed as a parameter.
	 *
	 * @param departmentUid <tt>String</tt> with the department's identifier. This
	 *                      parameter cannot be null.
	 * @param page          <tt>int</tt> with the page to start searching from. Must
	 *                      be greater or equal than 0.
	 * @param pageSize      <tt>int</tt> with the maximum number of results to
	 *                      return. Must be greater than 0 and lesser or equal than
	 *                      100.
	 * 
	 * @throws IdentifiableNotFoundException if the user isn't found.
	 */
	public List<UserDto> findAllWorkersOfDepartment(final String departmentUid, final int page, final int pageSize) {
		Objects.requireNonNull(departmentUid);
		this.validatePagination(page, pageSize);
		final List<User> users = this.departmentDao.findAllWorkersOfDepartment(departmentUid, page, pageSize);
		return this.userMapper.mapList(users);
	}

	/**
	 * Search for a department by their unique identifier.
	 *
	 * @param uid <tt>String</tt> with the unique identifier. This parameter cannot
	 *            be null.
	 *
	 * @return <tt>Department</tt> that has the same identifier as the one passed as
	 *         parameter.
	 * 
	 * @throws IdentifiableNotFoundException if the user isn't found.
	 */
	public DepartmentDto findDepartmentByUid(final String departmentUid) {
		Objects.requireNonNull(departmentUid);
		final Department department = this.departmentDao.findDepartmentByUid(departmentUid);
		return this.departmentMapper.map(department);
	}

	/**
	 * Insert a department in the database.
	 *
	 * @param department <tt>Department</tt> to insert. This parameter cannot be
	 *                   null
	 */
	public DepartmentDto insert(final DepartmentDto department) {
		Objects.requireNonNull(department);
		final Department departmentToPersist = new Department();
		this.merge(departmentToPersist, department);
		this.departmentDao.insert(departmentToPersist);
		return departmentMapper.map(departmentToPersist);
	}

	/**
	 * Update a department in the database.
	 *
	 * @param department <tt>Department</tt> to update. This parameter cannot be
	 *                   null
	 */
	public void update(final DepartmentDto department) {
		Objects.requireNonNull(department);
		final Department departmentFromDatabase = this.departmentDao.findDepartmentByUid(department.getUid());
		this.merge(departmentFromDatabase, department);
	}

	////////////////////////////////////////////////////////////////////////////
	// PRIVATE
	////////////////////////////////////////////////////////////////////////////

	private void merge(final Department department, final DepartmentDto departmentDto) {
		final SimpleUserDto headDto = departmentDto.getHead();
		final User head;
		if (headDto != null) {
			head = userDao.findUserByUid(departmentDto.getHead().getUid());
		} else {
			head = null;
		}
		department.setHead(head);
		department.setName(departmentDto.getName());
	}
}
