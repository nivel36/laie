package ged.ejb.curriculum.education;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;

@Stateless
public class EducationService extends AbstractService<Education> {

	@Inject
	@Repository
	private EducationDao educationDao;

	public List<Education> findByCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		return this.educationDao.findByCurriculum(curriculum);
	}

	@Override
	protected AbstractDao<Education> getDao() {
		return this.educationDao;
	}

}
