package es.nivel36.laie.ejb.core.file;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import jakarta.persistence.TypedQuery;

public class PhysicalFileJpaDao extends AbstractDao {

	public boolean isOrphanPhysicalFile(final PhysicalFile physicalFile) {
		final String jpql = """
				SELECT CASE WHEN (COUNT(f) > 0) THEN FALSE ELSE TRUE END
				FROM File f
				WHERE f.physicalFile = :physicalFile
				""";
		final TypedQuery<Boolean> query = this.em.createQuery(jpql, Boolean.class);
		query.setParameter("physicalFile", physicalFile);
		return query.getSingleResult();
	}

	public PhysicalFile findPhysicalFileByHashAndBucket(final String hash, final String bucket) {
		final String jpql = """
				SELECT f
				FROM PhysicalFile f
				WHERE f.contentHash = :hash
				AND f.bucket = :bucket
				""";
		final TypedQuery<PhysicalFile> query = this.em.createQuery(jpql, PhysicalFile.class);
		query.setParameter("hash", hash);
		query.setParameter("bucket", bucket);
		return query.getSingleResult();
	}
}