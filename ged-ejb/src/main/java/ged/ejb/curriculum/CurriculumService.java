package ged.ejb.curriculum;

import java.util.List;

public interface CurriculumService {

	void deleteCurriculum(Curriculum curriculum);

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

	Curriculum findByCandidateId(Long id);

	void insertCurriculum(Curriculum curriculum);

	Curriculum updateCurriculum(Curriculum curriculum);
}