package ged.ejb.curriculum;

import java.util.List;

import ged.ejb.core.Service;

public interface CurriculumService extends Service<Curriculum> {

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

	Curriculum findByCandidateId(long id);
}