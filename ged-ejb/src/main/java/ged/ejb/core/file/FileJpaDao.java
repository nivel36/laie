package ged.ejb.core.file;

import static ged.ejb.core.util.Parameters.map;

import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class FileJpaDao extends AbstractDao<File> {

	public boolean isOrphanPhysicalFile(PhysicalFile physicalFile) {
		return this.findByQuery(Boolean.class, "File.isOrphanPhysicalFile", map("physicalFile", physicalFile));
	}

	public PhysicalFile findPhysicalFileByHash(String hash) {
		try {
			return this.findByQuery(PhysicalFile.class, "File.findByHash", map("hash", hash));
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	protected Class<File> getType() {
		return File.class;
	}

}