package es.nivel36.laie.ejb.curriculum;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.curriculum.skill.Skill;

@Repository
public class CurriculumDao extends AbstractDao {

	public void insert(final Curriculum curriculum) {
		this.setUid(Curriculum.class, curriculum);
		this.insert(curriculum);
	}

	public void delete(final Curriculum curriculum) {
		this.delete(Curriculum.class, curriculum);
	}

	public Curriculum findByCandidateUid(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidateUid",
					map("candidateUid", candidateUid));
		} catch (final NoResultException e) {
			return null;
		}
	}

	public Curriculum findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Curriculum.class, "Curriculum.findByUid", map("uid", uid));
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name);
		return this.findByQuery(Skill.class, "Curriculum.Skill.findByName", map("name", name));
	}

	public List<CurriculumTemplate> findCurriculumTemplates() {
		return this.findAll(CurriculumTemplate.class, Page.ALL_RESULTS);
	}

	public SearchResult<Curriculum> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<Curriculum> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "skills.name", "jobExperiences.jobPosition",
				"jobExperiences.description", "jobExperiences.companyName", "educations.description" };
		return this.search(Curriculum.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}