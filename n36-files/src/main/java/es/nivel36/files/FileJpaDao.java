package es.nivel36.files;

import static es.nivel36.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Repository;
import es.nivel36.core.util.Parameters;

@Repository
public class FileJpaDao extends AbstractDao {

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

	public File findFileByUid(final String uid) {
		final String namedQuery = "File.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(File.class, namedQuery, parameters);
	}

	public void deletePhysicalFile(final PhysicalFile file) {
		Objects.requireNonNull(file);
		this.delete(PhysicalFile.class, file);
	}

	public void insert(final File file) {
		Objects.requireNonNull(file);
		this.setUid(File.class, file);
		this.em.persist(file);
	}

	public void delete(final File file) {
		Objects.requireNonNull(file);
		this.delete(File.class, file);
	}
}