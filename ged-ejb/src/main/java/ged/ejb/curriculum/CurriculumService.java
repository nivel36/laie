package ged.ejb.curriculum;

import java.util.List;

public interface CurriculumService {

	public void deleteCurriculum(Curriculum curriculum);

	public List<LanguageLevel> findAllLanguageLevels();

	public List<SkillLevel> findAllSkillLevels();

	public Curriculum findByCandidateId(Long id);

	public void insertCurriculum(Curriculum curriculum);

	public Curriculum updateCurriculum(Curriculum curriculum);

}