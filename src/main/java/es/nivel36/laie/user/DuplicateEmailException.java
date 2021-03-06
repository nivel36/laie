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
 * Exception thrown when trying to register a user with an email address that is
 * already registered by another user.
 * 
 * @author Abel Ferrer
 *
 */
public class DuplicateEmailException extends Exception {

	private static final long serialVersionUID = -8150450631266398087L;

	/**
	 * Constructs a <code>DuplicateEmailException</code> with no detail message.
	 */
	public DuplicateEmailException() {
	}

	/**
	 * Constructs a <code>DuplicateEmailException</code> with the specified detail
	 * message.
	 *
	 * @param message <tt>String</tt> with the detail message.
	 */
	public DuplicateEmailException(String message) {
		super(message);
	}
}
