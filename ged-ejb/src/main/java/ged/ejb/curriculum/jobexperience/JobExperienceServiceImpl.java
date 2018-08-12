package ged.ejb.curriculum.jobexperience;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;

@Stateless
public class JobExperienceServiceImpl extends AbstractService<JobExperience> implements JobExperienceService {

	private final JobExperienceDao jobExperienceDao;

	@Inject
	public JobExperienceServiceImpl(@Repository final JobExperienceDao jobExperienceDao) {
		this.jobExperienceDao = jobExperienceDao;
	}

	@Override
	public List<JobExperience> findByCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		return this.jobExperienceDao.findByCurriculum(curriculum);
	}

	@Override
	protected Dao<JobExperience> getDao() {
		return this.jobExperienceDao;
	}
}