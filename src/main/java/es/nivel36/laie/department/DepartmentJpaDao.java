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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.AbstractIdentifiableDao;
import es.nivel36.laie.core.service.IdentifiableNotFoundException;
import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.user.User;

/**
 * Data access object for the Department entity.
 *
 * @author Abel Ferrer
 *
 */
@Repository
public class DepartmentJpaDao extends AbstractIdentifiableDao {

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Searches for all departmens.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 1000.
	 */
	public List<Department> findAll(int page, int pageSize) {
		this.validatePagination(page, pageSize);
		final TypedQuery<Department> query = this.entityManager.createNamedQuery("Department.findAll",
				Department.class);
		this.paginate(page, pageSize, query);
		return query.getResultList();
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
	 *                      1000.
	 * 
	 * @throws IdentifiableNotFoundException if the user isn't found.
	 */
	public List<User> findAllWorkersOfDepartment(String departmentUid, int page, int pageSize) {
		Objects.requireNonNull(departmentUid);
		this.validatePagination(page, pageSize);
		try {
			final TypedQuery<User> query = this.entityManager.createNamedQuery("Department.findAllWorkersOfDepartment",
					User.class);
			this.paginate(page, pageSize, query);
			query.setParameter("departmentUid", departmentUid);
			return query.getResultList();
		} catch (NoResultException e) {
			throw new IdentifiableNotFoundException();
		}
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
	public Department findDepartmentByUid(String departmentUid) {
		Objects.requireNonNull(departmentUid);
		try {
			final TypedQuery<Department> query = this.entityManager.createNamedQuery("Department.findDepartmentByUid",
					Department.class);
			query.setParameter("departmentUid", departmentUid);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new IdentifiableNotFoundException();
		}
	}

	/**
	 * Insert a department in the database.
	 *
	 * @param department <tt>Department</tt> to insert. This parameter cannot be
	 *                   null
	 */
	public void insert(Department department) {
		Objects.requireNonNull(department);
		this.generateUid(department);
		this.entityManager.persist(department);
	}

	////////////////////////////////////////////////////////////////////////////
	// PROTECTED
	////////////////////////////////////////////////////////////////////////////

	protected boolean findDuplicateUid(final String uid) {
		final TypedQuery<Boolean> query = this.entityManager.createNamedQuery("Department.findDuplicateUid",
				Boolean.class);
		query.setParameter("uid", uid);
		return query.getSingleResult();
	}
}
