package ged.ejb.curriculum;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class CurriculumServiceImpl extends AbstractService<Long, Curriculum> implements CurriculumService {

	private static final Logger logger = Logger.getLogger(CurriculumServiceImpl.class.getName());

	private final CurriculumDao curriculumDao;

	@Inject
	public CurriculumServiceImpl(@Repository final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		logger.log(Level.FINE, "Find all the language levels");
		return this.curriculumDao.findAllLanguageLevels();
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		logger.log(Level.FINE, "Find all the skill levels");
		return this.curriculumDao.findAllSkillLevels();
	}

	@Override
	public Curriculum findByCandidateId(final Long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.log(Level.FINE, "Find curriculum by candidate id {0}", id);
		return this.curriculumDao.findByCandidateId(id);
	}

	@Override
	protected Dao<Long, Curriculum> getDao() {
		return this.curriculumDao;
	}
}