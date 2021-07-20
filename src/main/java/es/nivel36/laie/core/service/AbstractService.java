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
package es.nivel36.laie.core.service;

/**
 * Abstract class from which all Service's of the application must inherit.
 * 
 * @author Abel Ferrer
 *
 */
public abstract class AbstractService {

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	// The limit of the maximum number of results that can be searched.
	private static final int MAX_RESULT_SIZE = 1000;

	////////////////////////////////////////////////////////////////////////////
	// PROTECTED
	////////////////////////////////////////////////////////////////////////////

	protected void validatePagination(final int page, final int pageSize) {
		if (page < 0) {
			final String message = String.format("Bad page number: %d", page);
			throw new IllegalArgumentException(message);
		}
		if (pageSize <= 0) {
			final String message = String.format("Bad page size number: %d", pageSize);
			throw new IllegalArgumentException(message);
		}
		if (pageSize > MAX_RESULT_SIZE) {
			final String message = String.format("Page size number %d is out of limits. Maximum allowed value is %d",
					pageSize, MAX_RESULT_SIZE);
			throw new IllegalArgumentException(message);
		}
	}
}
