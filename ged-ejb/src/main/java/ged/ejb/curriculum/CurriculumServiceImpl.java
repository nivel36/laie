package ged.ejb.curriculum;

import java.util.List;
import java.util.Objects;
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
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}

	@Override
	public void deleteCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		logger.log(Level.FINE, "Delete curriculum of the candidate {}", curriculum.getCandidate());
		this.curriculumDao.delete(curriculum);
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
		logger.log(Level.FINE, "Find curriculum by candidate id {}", id);
		return this.curriculumDao.find(id);
	}

	@Override
	public void insertCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		logger.log(Level.FINE, "Insert curriculum of the candidate {}", curriculum.getCandidate());
		this.curriculumDao.insert(curriculum);
	}

	@Override
	public Curriculum updateCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		logger.log(Level.FINE, "Update curriculum of the candidate {}", curriculum.getCandidate());
		return this.curriculumDao.update(curriculum);
	}
}