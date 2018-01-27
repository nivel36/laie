package ged.ejb;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class UploadedServerFileDaoJpa extends AbstractDaoJpa<UploadedServerFile> implements UploadedServerFileDao {

	@Inject
	public UploadedServerFileDaoJpa(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<UploadedServerFile> getType() {
		return UploadedServerFile.class;
	}

}
