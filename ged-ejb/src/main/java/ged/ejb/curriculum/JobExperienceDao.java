package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobExperienceDao extends AbstractDao<JobExperience> {

	public JobExperience findByUid(final String uid) {
		return this.findByQuery(JobExperience.class, "JobExperience.findByUid", map("uid", uid));
	}

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "jobPosition", "description", "companyName" };
	}
}
