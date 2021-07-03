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
package es.nivel36.laie.core;

import java.io.Serializable;

/**
 * Interface to be implemented by any Data transfer object.
 * 
 * @author Abel Ferrer
 *
 */
public interface Dto extends Serializable {

	/**
	 * Returns the unique identifier of the user.<br/>
	 * This 6 digits value in hexadecimal is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @return <tt>String</tt> with the unique identifier of the user in the
	 *         application. This value is a six-character string in hexadecimal.
	 */
	String getUid();
}
