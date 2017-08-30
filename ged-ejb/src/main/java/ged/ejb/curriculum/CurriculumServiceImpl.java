package ged.ejb.curriculum;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	public Curriculum findByCandidateId(final long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.debug("Find curriculum by candidate id {}", id);
		return this.curriculumDao.findByCandidateId(id);
	}

	@Override
	protected Dao<Curriculum> getDao() {
		return this.curriculumDao;
	}
}