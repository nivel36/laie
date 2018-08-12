package ged.ejb.curriculum.jobexperience;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;
import ged.ejb.curriculum.Curriculum;

@Repository
public class JobExperienceDaoJpa extends AbstractDaoJpa<JobExperience> implements JobExperienceDao {

	@Override
	public List<JobExperience> findByCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		return this.findByQuery(JobExperience.class, "JobExperience.findByCurriculum", Parameters.map("curriculum", curriculum), 0, 0);
	}

	@Override
	protected Class<JobExperience> getType() {
		return JobExperience.class;
	}

	@Override
	public List<JobExperience> search(final String searchText) {
		return this.getPersistenceFacade().search(JobExperience.class, searchText, "description", "companyName", "jobPosition");
	}
}
