package ged.ejb.curriculum;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CurriculumDao {

	public void deleteCurriculum(Curriculum curriculum);

	public List<LanguageLevel> findAllLanguageLevels();

	public List<SkillLevel> findAllSkillLevels();

	public Curriculum findByCandidateId(final Long id);

	public void insertCurriculum(Curriculum curriculum);

	public Curriculum updateCurriculum(Curriculum curriculum);

}
