package ged.ejb.candidate;

import static ged.ejb.core.model.QueryParameter.with;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.core.FileType;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDaoJpa extends AbstractDaoJpa<Candidate> implements CandidateDao {

	@Inject
	public CandidateDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final List<Candidate> candidates;
		try {
			candidates = findByTypedQuery(Candidate.class, "Candidate.findAllByJobOffer",
					with("jobOffer", jobOffer).parameters(), 0, 0);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
		return candidates;
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return findAll(FileType.class);
	}

	@Override
	public Candidate findCandidateAndFiles(final long id) {
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return findByTypedQuery(Candidate.class, "Candidate.findCandidateAndFilesById", with("id", id).parameters());
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename, final String position,
			final boolean showDeleted) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (surename != null) {
			bj.must(qb.keyword().onField("surename").matching(surename).createQuery());
		}
		if (position != null) {
			bj.must(qb.keyword().onField("position").matching(position).createQuery());
		}
		if (!showDeleted) {
			bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		}
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), Candidate.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), Candidate.class);
		}
		return persistenceQuery.getResultList();
	}
}