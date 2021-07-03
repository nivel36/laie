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

/**
 * <p>
 * The role the user will have in the application.<br/>
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
 * </p>
 * 
 * @author Abel Ferrer
 *
 */
public enum Role {

	ADMIN, HHRR, USER;
}
