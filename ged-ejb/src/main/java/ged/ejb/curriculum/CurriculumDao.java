package ged.ejb.curriculum;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Dao;

public interface CurriculumDao extends Dao<Curriculum> {

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

	Curriculum findByCandidate(Candidate candidate);
}