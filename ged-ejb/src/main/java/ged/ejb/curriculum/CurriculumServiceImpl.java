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
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class CurriculumServiceImpl extends AbstractService<Curriculum> implements CurriculumService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final CurriculumDao curriculumDao;

	@Inject
	public CurriculumServiceImpl(@Repository final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		logger.debug("Find all the language levels");
		return this.curriculumDao.findAllLanguageLevels();
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		logger.debug("Find all the skill levels");
		return this.curriculumDao.findAllSkillLevels();
	}

	@Override
	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find curriculum by candidate  {}", candidate);
		return this.curriculumDao.findByCandidate(candidate);
	}

	@Override
	protected Dao<Curriculum> getDao() {
		return this.curriculumDao;
	}
}