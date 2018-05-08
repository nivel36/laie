package ged.ejb.curriculum;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.Service;

public interface CurriculumService extends Service<Curriculum> {

	List<LanguageLevel> findAllLanguageLevels();

	List<SkillLevel> findAllSkillLevels();

	Curriculum findByCandidate(Candidate candidate);
}