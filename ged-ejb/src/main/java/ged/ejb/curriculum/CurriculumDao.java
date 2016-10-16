package ged.ejb.curriculum;

import java.util.List;

public interface CurriculumDao {

	void deleteCurriculum(final Curriculum curriculum);

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

	Curriculum findByCandidateId(final Long id);

	void insertCurriculum(final Curriculum curriculum);

	Curriculum updateCurriculum(final Curriculum curriculum);
}