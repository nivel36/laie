package ged.ejb.curriculum.jobexperience;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class JobExperienceServiceImpl extends AbstractService<JobExperience> implements JobExperienceService {

	private final JobExperienceDao jobExperienceDao;

	@Inject
	public JobExperienceServiceImpl(@Repository final JobExperienceDao jobExperienceDao) {
		this.jobExperienceDao = jobExperienceDao;
	}

	@Override
	protected Dao<JobExperience> getDao() {
		return this.jobExperienceDao;
	}
}