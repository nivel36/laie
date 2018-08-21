package ged.ejb.curriculum.education;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class EducationService extends AbstractService<Education> {

	@Inject
	@Repository
	private EducationDao educationDao;

	@Override
	protected AbstractDao<Education> getDao() {
		return this.educationDao;
	}

}
