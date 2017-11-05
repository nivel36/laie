package ged.ejb.candidate;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class FileSysDaoJpa extends AbstractDaoJpa<UploadedServerFile> implements FileSysDao {

	@Inject
	public FileSysDaoJpa(final EntityManager em) {
		super(em);
	}

	@Override
	protected Class<UploadedServerFile> getType() {
		return UploadedServerFile.class;
	}
}
