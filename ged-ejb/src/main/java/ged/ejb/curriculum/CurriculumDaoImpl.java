package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;

import ged.ejb.candidate.Candidate;
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
	public Curriculum findByCandidate(final Candidate candidate) {
		return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
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