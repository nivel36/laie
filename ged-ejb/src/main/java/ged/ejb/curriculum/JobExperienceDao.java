package ged.ejb.curriculum;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobExperienceDao extends AbstractDao<JobExperience> {

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}
}
