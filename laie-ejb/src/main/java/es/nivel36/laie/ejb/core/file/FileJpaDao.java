package es.nivel36.laie.ejb.core.file;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.UidGenerator;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class FileJpaDao extends AbstractDao {

	public boolean isOrphanPhysicalFile(final PhysicalFile physicalFile) {
		final String namedQuery = "File.isOrphanPhysicalFile";
		final Parameters parameters = map("physicalFile", physicalFile);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public PhysicalFile findPhysicalFileByHash(final String hash) {
		final String namedQuery = "File.findByHash";
		final Parameters parameters = map("hash", hash);
		return this.findByQuery(PhysicalFile.class, namedQuery, parameters);
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
		this.setUid(file);
		this.em.persist(file);
	}
	
	private void setUid(File file) {
		String uid;
		do {
			uid = UidGenerator.generate(File.class);
			file.setUid(uid);
		} while (!this.checkDuplicateUid(uid));
	}

	private boolean checkDuplicateUid(final String uid) {
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Boolean.class, "User.checkDuplicateUid", parameters);
	}

	public void delete(final File file) {
		Objects.requireNonNull(file);
		this.delete(File.class, file);
	}
}