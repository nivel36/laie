package ged.ejb.curriculum.jobexperience;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobExperienceDao extends AbstractDao<JobExperience> {

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}

	@Override
	public List<JobExperience> search(final String searchText) {
		return this.getPersistenceFacade().search(JobExperience.class, searchText, "description", "companyName", "jobPosition");
	}
}
