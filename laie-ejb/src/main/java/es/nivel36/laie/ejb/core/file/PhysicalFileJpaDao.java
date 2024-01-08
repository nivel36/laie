package es.nivel36.laie.ejb.core.file;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.persistence.NoResultException;


public class PhysicalFileJpaDao extends AbstractDao {

	public boolean isOrphanPhysicalFile(final PhysicalFile physicalFile) {
		final String namedQuery = "File.isOrphanPhysicalFile";
		final Parameters parameters = map("physicalFile", physicalFile);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public PhysicalFile findPhysicalFileByHashAndBucket(final String hash, final String bucket) {
		try {
			final String namedQuery = "File.findByHashAndBucket";
			final Parameters parameters = map("hash", hash).and("bucket", bucket);
			return this.findByQuery(PhysicalFile.class, namedQuery, parameters);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void deletePhysicalFile(final PhysicalFile file) {
		Objects.requireNonNull(file);
		this.delete(PhysicalFile.class, file);
	}
}