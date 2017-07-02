package ged.ejb.curriculum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDaoImpl extends AbstractDao<Curriculum> implements CurriculumDao {

	@Inject
	public CurriculumDaoImpl(final EntityManager entityManger) {
		super(entityManger);
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return findAll(LanguageLevel.class);
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return findAll(SkillLevel.class);
	}

	@Override
	public Curriculum findByCandidateId(final long candidateId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("candidateId", candidateId);
		return findByTypedQuery(Curriculum.class, "Curriculum.findByCandidateId", parameters);
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}
}