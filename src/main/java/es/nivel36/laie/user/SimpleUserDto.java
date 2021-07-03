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

import java.nio.file.Path;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.Dto;

/**
 * <p>
 * Data transfer object (DTO) representing the basic data of the user of the
 * application.
 * </p>
 * 
 * @author Abel Ferrer
 * 
 * @see UserDto
 * @see User
 */
public class SimpleUserDto implements Dto {

	private static final long serialVersionUID = -7202813450383521723L;

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@NotNull
	private String fullName;

	private Path image;

	@NotNull
	private String uid;

	////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * A basic constructor
	 */
	public SimpleUserDto() {
	}

	/**
	 * A constructor with all the parameters of the class. 
	 * 
	 * @param uid      <tt>String</tt> with the unique identifier of the user in the
	 *                 application. This value is a six-character string in
	 *                 hexadecimal. This value can not be null
	 * @param fullName <tt>String</tt> with the full name of the user. This value
	 *                 can not be null.
	 * @param image    <tt>Path</tt> with the path of the user's image or
	 *                 <tt>null</tt> if the user has no image.
	 */
	public SimpleUserDto(final String uid, final String fullName, final Path image) {
		Objects.requireNonNull(uid);
		Objects.requireNonNull(fullName);
		this.uid = uid;
		this.fullName = fullName;
		this.image = image;
	}

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimpleUserDto other = (SimpleUserDto) obj;
		return Objects.equals(uid, other.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}

	@Override
	public String toString() {
		return fullName;
	}

	////////////////////////////////////////////////////////////////////////////
	// GETTERS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the full name of the user.
	 * 
	 * @return <tt>String</tt> with the full name of the user.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Returns the image of the user
	 * 
	 * @return <tt>File</tt> with the user's image or <tt>null</tt> if the user has
	 *         no image.
	 */
	public Path getImage() {
		return image;
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

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////
	
	void setFullName(String fullName) {
		this.fullName = fullName;
	}

	void setImage(Path image) {
		this.image = image;
	}

	void setUid(String uid) {
		this.uid = uid;
	}
}
