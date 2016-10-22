package ged.ejb.curriculum;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.model.Repository;

@Stateless
public class CurriculumServiceImpl implements CurriculumService {

	private static final Logger logger = Logger.getLogger(CurriculumServiceImpl.class.getName());

	private final CurriculumDao curriculumDao;

	@Inject
	public CurriculumServiceImpl(@Repository final CurriculumDao curriculumDao) {
		if (curriculumDao == null) {
			throw new NullPointerException();
		}
		this.curriculumDao = curriculumDao;
	}

	@Override
	public void deleteCurriculum(final Curriculum curriculum) {
		if (curriculum == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Delete curriculum of the candidate {}", curriculum.getCandidate());
		this.curriculumDao.deleteCurriculum(curriculum);
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
		if (id == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Find curriculum by candidate id {}", id);
		return this.curriculumDao.findByCandidateId(id);
	}

	@Override
	public void insertCurriculum(final Curriculum curriculum) {
		if (curriculum == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Insert curriculum of the candidate {}", curriculum.getCandidate());
		this.curriculumDao.insertCurriculum(curriculum);
	}

	@Override
	public Curriculum updateCurriculum(final Curriculum curriculum) {
		if (curriculum == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Update curriculum of the candidate {}", curriculum.getCandidate());
		return this.curriculumDao.updateCurriculum(curriculum);
	}
}