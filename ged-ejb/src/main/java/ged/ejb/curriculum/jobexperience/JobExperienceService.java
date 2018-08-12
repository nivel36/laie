package ged.ejb.curriculum.jobexperience;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.curriculum.Curriculum;

public interface JobExperienceService extends Service<JobExperience> {

	List<JobExperience> findByCurriculum(Curriculum curriculum);

}