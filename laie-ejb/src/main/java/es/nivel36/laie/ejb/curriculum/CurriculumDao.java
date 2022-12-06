package es.nivel36.laie.ejb.curriculum;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import org.apache.lucene.search.SortField;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.SearchSort;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;


public class CurriculumDao extends AbstractDao {

	@Inject
	private SearchFacade searchFacade;

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		try {
			return this.findByQuery(Curriculum.class, "Curriculum.findByCandidate", map("candidate", candidate));
		} catch (final NoResultException e) {
			return null;
		}
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name);
		try {
			return this.findByQuery(Skill.class, "Curriculum.Skill.findByName", map("name", name));
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

	public SearchResult<Curriculum> search(final String searchText, final Page page,  final SearchSort sortOrder,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "skills.name", "jobExperiences.jobPosition",
				"jobExperiences.description", "jobExperiences.companyName", "educations.description" };
		return searchFacade.search(Curriculum.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}