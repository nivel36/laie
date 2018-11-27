package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;

import javax.persistence.NoResultException;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.language.LanguageLevel;
import ged.ejb.curriculum.skills.SkillLevel;

@Repository
public class CurriculumDao extends AbstractDao<Curriculum> {

	public List<LanguageLevel> findAllLanguageLevels() {
		return this.findAll(LanguageLevel.class, Page.ALL);
	}

	public List<SkillLevel> findAllSkillLevels() {
		return this.findAll(SkillLevel.class, Page.ALL);
	}

	public Curriculum findByCandidate(final Candidate candidate) {
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
		}
		catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}