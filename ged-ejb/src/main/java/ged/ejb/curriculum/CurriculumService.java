package ged.ejb.curriculum;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.language.LanguageLevel;
import ged.ejb.curriculum.skills.SkillLevel;

@Stateless
public class CurriculumService extends AbstractService<Curriculum> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private CurriculumDao curriculumDao;

	public List<LanguageLevel> findAllLanguageLevels() {
		logger.debug("Find all the language levels");
		return this.curriculumDao.findAllLanguageLevels();
	}

	public List<SkillLevel> findAllSkillLevels() {
		logger.debug("Find all the skill levels");
		return this.curriculumDao.findAllSkillLevels();
	}

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find curriculum by candidate  {}", candidate);
		return this.curriculumDao.findByCandidate(candidate);
	}

	@Override
	protected AbstractDao<Curriculum> getDao() {
		return this.curriculumDao;
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}
}