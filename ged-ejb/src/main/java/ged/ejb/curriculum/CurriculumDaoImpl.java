package ged.ejb.curriculum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDaoImpl extends AbstractDaoJpa<Curriculum> implements CurriculumDao {

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return this.findAll(LanguageLevel.class);
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return this.findAll(SkillLevel.class);
	}

	@Override
	public Curriculum findByCandidateId(final long candidateId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("candidateId", candidateId);
		return this.findByQuery(Curriculum.class, "Curriculum.findByCandidateId", parameters);
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}

	@Override
	public List<Curriculum> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}