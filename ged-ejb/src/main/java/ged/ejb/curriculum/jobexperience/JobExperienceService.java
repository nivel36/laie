package ged.ejb.curriculum.jobexperience;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class JobExperienceService extends AbstractService<JobExperience> {

	@Inject
	@Repository
	private JobExperienceDao jobExperienceDao;

	@Override
	protected AbstractDao<JobExperience> getDao() {
		return this.jobExperienceDao;
	}

	@Override
	public JobExperience save(final JobExperience jobExperience) {
		Objects.requireNonNull(jobExperience);
		this.validateDates(jobExperience);
		return super.save(jobExperience);
	}

	public void setJobExperienceDao(final JobExperienceDao jobExperienceDao) {
		this.jobExperienceDao = jobExperienceDao;
	}

	private void validateDates(final JobExperience jobExperience) {
		if (jobExperience.getStartDate() == null) {
			throw new IllegalStateException("Start date is null");
		}
		if ((jobExperience.getEndDate() == null) && !jobExperience.isStillWorking()) {
			throw new IllegalStateException("End date is null");
		}
		if (jobExperience.getEndDate().isBefore(jobExperience.getStartDate())) {
			throw new IllegalStateException("End date cannot be beofere start date");
		}
	}
}