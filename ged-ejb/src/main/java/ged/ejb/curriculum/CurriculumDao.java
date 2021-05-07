package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDao extends AbstractIndexedDao<Curriculum> {

	public List<CurriculumTemplate> findCurriculumTemplates() {
		return this.findAll(CurriculumTemplate.class, Page.ALL_RESULTS);
	}

	public Curriculum findByCandidateUid(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidateUid", map("candidateUid", candidateUid));
		} catch (final NoResultException e) {
			return null;
		}
	}
	
	public Skill findSkill(final String name) {
		Objects.requireNonNull(name);
		return this.findByQuery(Skill.class, "Skill.findByName", map("name", name));
	}

	public Curriculum findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Curriculum.class, "Curriculum.findByUid", map("uid", uid));
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "skills.name", "jobExperiences.jobPosition", "jobExperiences.description",
				"jobExperiences.companyName", "educations.description" };
	}
}