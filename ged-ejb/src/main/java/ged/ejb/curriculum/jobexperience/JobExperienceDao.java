package ged.ejb.curriculum.jobexperience;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobExperienceDao extends AbstractDao<JobExperience> {

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "description", "companyName", "jobPosition" };
	}
}
