package ged.ejb.curriculum.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumDao;
import ged.ejb.curriculum.LanguageLevel;
import ged.ejb.curriculum.SkillLevel;

@Repository
public class CurriculumDaoImpl implements CurriculumDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public void deleteCurriculum(final Curriculum curriculum) {
		this.persistenceFacade.delete(curriculum);
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return this.persistenceFacade.findAll(LanguageLevel.class);
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return this.persistenceFacade.findAll(SkillLevel.class);
	}

	@Override
	public Curriculum findByCandidateId(final Long id) {
		Curriculum curriculum = null;
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("candidateId", id);
			curriculum = this.persistenceFacade.findByTypedQuery(Curriculum.class,
					"Curriculum.getByCandidateId", params);
		} catch (final NoResultException ex) {
			curriculum = null;
		}
		return curriculum;
	}

	@Override
	public void insertCurriculum(final Curriculum curriculum) {
		this.persistenceFacade.insert(curriculum);
	}

	@Override
	public Curriculum updateCurriculum(final Curriculum curriculum) {
		return this.persistenceFacade.update(curriculum);
	}

}
