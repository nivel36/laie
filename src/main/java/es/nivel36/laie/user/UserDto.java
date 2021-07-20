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

import java.util.Objects;

import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.Dto;
import es.nivel36.laie.department.SimpleDepartmentDto;

/**
 * <p>
 * Data transfer object (DTO) representing a user of the application.
 * </p>
 * 
 * @author Abel Ferrer
 */
public final class UserDto implements Dto {

	private static final long serialVersionUID = -6357240024287869443L;
	
	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	private SimpleDepartmentDto department;

	@NotNull
	private String email;

	private String idNumber;

	private String image;

	private SimpleUserDto manager;

	private String locale;

	@NotNull
	private String name;

	private String phoneNumber;

	@NotNull
	private Role role;

	@NotNull
	private String surname;

	@NotNull
	private String uid;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final UserDto other = (UserDto) obj;
		return Objects.equals(this.uid, other.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uid);
	}

	@Override
	public String toString() {
		return this.name + " " + this.surname;
	}

	///////////////////////////////////////////////////////////////////////////
	// GETTERS
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the user's department's identifier.<br/>
	 * 
	 * @return <tt>String</tt> with the user's departament's identifier or
	 *         <tt>null</tt> if the user has no department assigned
	 */
	public SimpleDepartmentDto getDepartment() {
		return this.department;
	}

	/**
	 * Returns the user's email address.<br/>
	 * This value is mandatory and cannot be duplicated.<br/>
	 * The user will receive notifications from the application in this email.
	 * 
	 * @return <tt>String</tt> with the user's email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Returns the identifier of the employee in the company.<br/>
	 * This value may not be duplicated, but may be null.
	 * 
	 * @return <tt>String</tt> with the user's id number or <tt>null</tt> if the
	 *         user has no id.
	 */
	public String getIdNumber() {
		return this.idNumber;
	}

	/**
	 * Returns the path of the user's image.<br/>
	 * 
	 * @return <tt>String</tt> with the path of the user's image or <tt>null</tt> if
	 *         the user has no image.
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * Returns the user's locale.
	 * 
	 * @return <tt>String</tt> with the user's locale.
	 * @see java.util.Locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Returns the full name of the user's manager. If the user has no manager, this
	 * value is null.
	 * 
	 * @return <tt>String</tt> with the full name of the user's manager or
	 *         <tt>null</tt> if the user has no manager
	 */
	public SimpleUserDto getManager() {
		return this.manager;
	}

	/**
	 * Returns the name of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @return <tt>String</tt> with the user's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the phone number of the user.
	 * 
	 * @return <tt>String</tt> with the user's phone number or <tt>null</tt> if the
	 *         user has no phone.
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * Returns the role of the user in the application.<br/>
	 * It has three values:
	 * <ul>
	 * <li><b>ADMIN</b>: This is the value of the administrator. This user can
	 * manage all the configuration values of the application as well as register or
	 * modify user values and act as manager for a user even if he/she is not.</li>
	 * <li><b>HHRR</b>: This is the value of the human resources personnel. This
	 * user can register or modify user values and act as manager for a user even if
	 * he/she is not.</li>
	 * <li><b>USER</b>: This is the default value with no special permissions beyond
	 * configuring his profile and acting as manager, if he/she is, for another
	 * user.</li>
	 * </ul>
	 * This value cannot be null.
	 * 
	 * @return <tt>Role</tt> with the role of the user in the application.
	 * @see Role
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * Returns the surname of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @return <tt>String</tt> with the user's surname.
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * Returns the unique identifier of the user.<br/>
	 * This 6 digits hexadecimal value is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @return <tt>String</tt> with the unique identifier of the user in the
	 *         application. This value is a six-character hexadecimal.
	 */
	public String getUid() {
		return this.uid;
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////
	
	void setDepartment(final SimpleDepartmentDto department) {
		this.department = department;
	}

	/**
	 * Sets the user's email address.<br/>
	 * This value is mandatory and cannot be duplicated.<br/>
	 * The user will receive notifications from the application in this email.
	 * 
	 * @param email <tt>String</tt> with the user's email.
	 */
	public void setEmail(final String email) {
		Objects.requireNonNull(email);
		this.email = email;
	}

	/**
	 * Sets the identifier of the employee in the company.<br/>
	 * This value may not be duplicated, but may be null.
	 * 
	 * @param idNumber <tt>String</tt> with the user's id number or <tt>null</tt> if
	 *                 the user has no id.
	 * 
	 */
	public void setIdNumber(final String idNumber) {
		this.idNumber = idNumber;
	}
	
	void setImage(final String image) {
		this.image = image;
	}

	/**
	 * Sets the user's locale. This value can not be null.
	 * 
	 * @param locale <tt>Locale</tt> with the user's Locale. This value can not be
	 *               null.
	 * @see java.util.Locale
	 */
	public void setLocale(final String locale) {
		this.locale = locale;
	}

	void setManager(final SimpleUserDto manager) {
		this.manager = manager;
	}

	/**
	 * Sets the name of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @param name <tt>String</tt> with the user's name.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the phone number of the user.
	 * 
	 * @param phoneNumber <tt>String</tt> with the user's phone number or
	 *                    <tt>null</tt> if the user has no phone.
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the role of the user in the application.<br/>
	 * There are three possible values:
	 * <ul>
	 * <li><b>ADMIN</b>: This is the value of the application administrator. This
	 * user can manage all the configuration values of the application as well as
	 * register or modify user values and act as manager for a user even if he/she
	 * is not.</li>
	 * <li><b>HHRR</b>: This is the value of the human resources personnel. This
	 * user can register or modify user values and act as manager for a user even if
	 * he/she is not.</li>
	 * <li><b>USER</b>: This is the default value with no special permissions beyond
	 * configuring his profile and acting as manager, if he/she is, for another
	 * user.</li>
	 * </ul>
	 * This value cannot be null.
	 * 
	 * @param role <tt>Role</tt> with the role of the user in the application.
	 * 
	 * @see Role
	 */
	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * Sets the surname of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @param surname <tt>String</tt> with the user's surname.
	 */
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	void setUid(final String uid) {
		this.uid = uid;
	}
}