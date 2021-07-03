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
package es.nivel36.laie.file;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.Repository;

@Repository
public class FileJpaDao {

	@PersistenceContext
	private EntityManager entityManager;

	public File find(final long fileId) {
		return this.entityManager.find(File.class, fileId);
	}

	public PhysicalFile findPhysicalFileByHash(final String hash) {
		try {
			final TypedQuery<PhysicalFile> q = this.entityManager.createQuery("File.findByHash", PhysicalFile.class);
			q.setParameter("hash", hash);
			return q.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public boolean isOrphan(final long physicalFileId) {
		final TypedQuery<Boolean> q = this.entityManager.createQuery("PhysicalFile.isOrphan", Boolean.class);
		q.setParameter("physicalFileId", physicalFileId);
		return q.getSingleResult();
	}

	public void remove(final File file) {
		this.entityManager.remove(file);
	}

	public File save(final File file) {
		return this.entityManager.merge(file);
	}

	public void setEntityManager(final EntityManager entityManager) {
		Objects.requireNonNull(entityManager);
		this.entityManager = entityManager;
	}

}
