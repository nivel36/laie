package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDao extends AbstractDao<Curriculum> {

	public List<SkillLevel> findAllSkillLevels() {
		return this.findAll(SkillLevel.class, Page.ALL);
	}

	public Curriculum findByCandidate(final Candidate candidate) {
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
		} catch (final NoResultException e) {
			return null;
		}
	}
	
	public Skill findSkill(final String name) {
		Objects.requireNonNull(name, "Skill name can't be null");
		try {
			return this.findByQuery(Skill.class, "Skill.findByName", map("name", name));
		} catch (final NoResultException e) {
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