package ged.ejb;

import java.util.List;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class UploadedServerFileDaoJpa extends AbstractDaoJpa<UploadedServerFile> implements UploadedServerFileDao {

	@Override
	protected Class<UploadedServerFile> getType() {
		return UploadedServerFile.class;
	}

	@Override
	public List<UploadedServerFile> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}