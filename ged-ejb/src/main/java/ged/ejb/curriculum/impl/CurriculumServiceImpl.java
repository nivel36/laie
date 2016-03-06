package ged.ejb.curriculum.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumDao;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;

@Stateless
public class CurriculumServiceImpl implements CurriculumService {

	@Inject
	@Repository
	private CurriculumDao curriculumDao;

	@Override
	public void deleteCurriculum(final Curriculum curriculum) {
		this.curriculumDao.deleteCurriculum(curriculum);

	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return this.curriculumDao.findAllLanguageLevels();
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return this.curriculumDao.findAllSkillLevels();
	}

	@Override
	public Curriculum findByCandidateId(final Long id) {
		return this.curriculumDao.findByCandidateId(id);
	}

	@Override
	public void insertCurriculum(final Curriculum curriculum) {
		this.curriculumDao.insertCurriculum(curriculum);
	}

	@Override
	public Curriculum updateCurriculum(final Curriculum curriculum) {
		return this.curriculumDao.updateCurriculum(curriculum);
	}
}
