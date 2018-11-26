package ged.ejb.curriculum.jobexperience;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class JobExperienceDao extends AbstractDao<JobExperience> {

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}

	@Override
	public List<JobExperience> search(final String searchText, final Page page) {
		return this.getPersistenceFacade().search(page, JobExperience.class, searchText, "description", "companyName", "jobPosition");
	}
}
