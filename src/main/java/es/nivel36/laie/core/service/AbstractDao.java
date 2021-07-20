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

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Abstract class from which all Dao's of the application must inherit.
 * 
 * @author Abel Ferrer
 *
 */
public abstract class AbstractDao {

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	// The limit of the maximum number of results that can be searched.
	protected static final int MAX_RESULT_SIZE = 1000;

	@PersistenceContext
	protected EntityManager entityManager;

	////////////////////////////////////////////////////////////////////////////
	// PROTECTED
	////////////////////////////////////////////////////////////////////////////

	protected abstract boolean findDuplicateUid(final String uid);

	protected void generateUid(final Identifiable identifiable) {
		final RandomGenerator uidGenerator = new RandomGenerator(6);
		// The identifier is a randomly generated number. Although it generates a number
		// out of 16,777,216 and it is difficult for any number to be repeated, we must
		// always verify that the value is not duplicated.
		String uid;
		do {
			uid = uidGenerator.generateHex();
		} while (this.findDuplicateUid(uid));
		identifiable.setUid(uid);
	}

	protected <T> void paginate(final int page, final int pageSize, final TypedQuery<T> query) {
		query.setFirstResult(page * pageSize);
		query.setMaxResults(pageSize);
	}

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

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////

	public void setEntityManager(final EntityManager entityManager) {
		Objects.requireNonNull(entityManager);
		this.entityManager = entityManager;
	}
}
