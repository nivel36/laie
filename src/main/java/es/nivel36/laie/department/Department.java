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

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;
import es.nivel36.laie.core.service.Identifiable;
import es.nivel36.laie.user.User;

/**
 * A department is a unit in which different users of the company that have a
 * common work or objective are grouped together.
 * 
 * @author Abel Ferrer
 *
 */
@Entity
public class Department extends AbstractEntity implements Identifiable {

	private static final long serialVersionUID = 6861552455672978101L;

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	private User head;

	@NotNull
	@Column(nullable = false, unique = true)
	private String name;

	@NotNull
	@Column(nullable = false, unique = true)
	private String uid;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private Set<User> workers;

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Department other = (Department) obj;
		return Objects.equals(this.name, other.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.name);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.name;
	}

	///////////////////////////////////////////////////////////////////////////
	// GETTERS
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the head of department. This manager does not necessarily have to be
	 * responsible for all users in the department.
	 * 
	 * @return <tt>User</tt> with the head of department or <tt>null</tt> if
	 *         department has no head.
	 */
	public User getHead() {
		return this.head;
	}

	/**
	 * Returns the name of the department. This value must be unique and cannot be
	 * null.
	 * 
	 * @return <tt>String</tt> with the name of department.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUid() {
		return this.uid;
	}

	/**
	 * Returns the set of users that are part of the department.
	 * 
	 * @return <tt>Set</tt> with the users of the department.
	 */
	public Set<User> getWorkers() {
		return this.workers;
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Set the head of department. This manager does not necessarily have to be
	 * responsible for all users in the department.
	 * 
	 * @param user <tt>User</tt> with the head of department or <tt>null</tt> if
	 *             department has no head.
	 */
	public void setHead(final User head) {
		this.head = head;
	}

	/**
	 * Sets the name of the department. This value must be unique and cannot be
	 * <tt>null</tt>.
	 * 
	 * @param name <tt>String</tt> with the name of department.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUid(final String uid) {
		this.uid = uid;
	}

	/**
	 * Sets the set of users that are part of the department.
	 * 
	 * @param workers <tt>Set</tt> with the users of the department.
	 */
	public void setWorkers(final Set<User> workers) {
		this.workers = workers;
	}
}
