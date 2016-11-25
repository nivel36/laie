package ged.ejb.curriculum;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface CurriculumDao extends Dao<Long, Curriculum> {

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

}