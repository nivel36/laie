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

import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.Dto;

/**
 * <p>
 * Data transfer object (DTO) representing the basic data of the department of
 * the application.
 * </p>
 * 
 * @author Abel Ferrer
 * 
 * @see DepartmentDto
 * @see Department
 */
public class SimpleDepartmentDto implements Dto {

	private static final long serialVersionUID = -3558861134640388344L;

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@NotNull
	private String name;

	@NotNull
	private String uid;

	////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * A basic constructor
	 */
	public SimpleDepartmentDto() {
	}

	/**
	 * A constructor with all the parameters of the class.
	 * 
	 * @param uid  <tt>String</tt> with the unique identifier of the department in
	 *             the application. This value is a six-character string in
	 *             hexadecimal. This value can not be null
	 * @param name <tt>String</tt> with the name of department. This value can not
	 *             be null.
	 */
	public SimpleDepartmentDto(final String uid, final String name) {
		Objects.requireNonNull(uid);
		Objects.requireNonNull(name);
		this.uid = uid;
		this.name = name;
	}

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		final SimpleDepartmentDto other = (SimpleDepartmentDto) obj;
		return Objects.equals(this.name, other.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.name;
	}

	////////////////////////////////////////////////////////////////////////////
	// GETTERS
	////////////////////////////////////////////////////////////////////////////

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

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the name of the department. This value must be unique and cannot be
	 * <tt>null</tt>.
	 * 
	 * @param name <tt>String</tt> with the name of department.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	void setUid(final String uid) {
		this.uid = uid;
	}
}
