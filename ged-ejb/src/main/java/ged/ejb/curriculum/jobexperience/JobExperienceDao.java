package ged.ejb.curriculum.jobexperience;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.curriculum.Curriculum;

public interface JobExperienceDao extends Dao<JobExperience> {

	List<JobExperience> findByCurriculum(Curriculum curriculum);

}