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

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.service.AbstractEntity;
import es.nivel36.laie.service.Identifiable;

/**
 * Entity representing the identity data of a user.
 * 
 * @author Abel Ferrer
 *
 */
@Entity
public class User extends AbstractEntity implements Identifiable {

	private static final long serialVersionUID = 3946497561340595866L;

	@NotNull
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(nullable = false, unique = true)
	private String idNumber;

	@ManyToOne
	@JoinColumn(name = "managerId")
	private User manager;

	@NotNull
	@Column(nullable = false)
	private String name;

	private String phoneNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private String surname;

	@NotNull
	@Column(nullable = false, unique = true)
	private String uid;

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(uid, other.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}

	///////////////////////////////////////////////////////////////////////////
	// GETTERS
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the user's email address.<br/>
	 * This value is mandatory and cannot be duplicated.<br/>
	 * The user will receive notifications from the application in this email.
	 * 
	 * @return <tt>String</tt> with the user's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the identifier of the employee in the company.<br/>
	 * This value may not be duplicated, but may be null.
	 * 
	 * @return <tt>String</tt> with the user's id number or <tt>null</tt> if the
	 *         user has no id.
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * Returns the user's manager. If the user has no manager, this value is null.
	 * 
	 * @return <tt>User</tt> with the user's manager or <tt>null</tt> if the user
	 *         has no manager
	 */
	public User getManager() {
		return manager;
	}

	/**
	 * Returns the name of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @return <tt>String</tt> with the user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the phone number of the user.
	 * 
	 * @return <tt>String</tt> with the user's phone number or <tt>null</tt> if the
	 *         user has no phone.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
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
		return role;
	}

	/**
	 * Returns the surname of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @return <tt>String</tt> with the user's surname.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Returns the unique identifier of the user.<br/>
	 * This 6 digits value in hexadecimal is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @return <tt>String</tt> with the unique identifier of the user in the
	 *         application. This value is a six-character string in hexadecimal.
	 */
	public String getUid() {
		return uid;
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the user's email address.<br/>
	 * This value is mandatory and cannot be duplicated.<br/>
	 * The user will receive notifications from the application in this email.
	 * 
	 * @param email <tt>String</tt> with the user's email.
	 */
	public void setEmail(String email) {
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
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * Sets the user's manager. If the user has no manager, this value must be null.
	 * 
	 * @param manager <tt>User</tt> with the the user's manager or <tt>null</tt> if
	 *                the user has no manager.
	 */
	public void setManager(User manager) {
		this.manager = manager;
	}

	/**
	 * Sets the name of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @param name <tt>String</tt> with the user's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the phone number of the user.
	 * 
	 * @param phoneNumber <tt>String</tt> with the user's phone number or
	 *                    <tt>null</tt> if the user has no phone.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the role of the user in the application.<br/>
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
	 * @param role <tt>Role</tt> with the role of the user in the application.
	 * 
	 * @see Role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Sets the surname of the user.<br/>
	 * This value cannot be null.
	 * 
	 * @param surname <tt>String</tt> with the user's surname.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Sets the unique identifier of the user.<br/>
	 * This 6 digits value in hexadecimal is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @param uid <tt>String</tt> with the unique identifier of the user in the
	 *            application. This value is a six-character string in hexadecimal.
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
}
