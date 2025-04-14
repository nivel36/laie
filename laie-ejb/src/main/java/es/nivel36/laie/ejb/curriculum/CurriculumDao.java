package es.nivel36.laie.ejb.curriculum;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CurriculumDao extends AbstractDao {

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		try {
			final String jpql = """
					SELECT c
					FROM Curriculum c
					LEFT JOIN FETCH c.education
					LEFT JOIN FETCH c.jobExperiences
					LEFT JOIN FETCH c.languages
					LEFT JOIN FETCH c.skills
					WHERE c.candidate = :candidate
					""";
			final TypedQuery<Curriculum> query = this.em.createQuery(jpql, Curriculum.class);
			query.setParameter("candidate", candidate);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name);
		try {
			final String jpql = """
					SELECT s
					FROM Skill s
					WHERE s.name = :name
					""";
			final TypedQuery<Skill> query = this.em.createQuery(jpql, Skill.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public List<CurriculumTemplate> findCurriculumTemplates() {
		return this.findAll(CurriculumTemplate.class, Page.ALL_RESULTS);
	}

	public SearchResult<Curriculum> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<Curriculum> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "skills.name", "jobExperiences.jobPosition",
				"jobExperiences.description", "jobExperiences.companyName", "educations.description" };
		return searchFacade.search(Curriculum.class, page, sortField, searchFacets, searchText, searchFields);
	}
}