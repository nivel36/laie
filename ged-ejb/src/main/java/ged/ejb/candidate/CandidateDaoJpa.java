package ged.ejb.candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.FileType;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDaoJpa extends AbstractDao<Candidate> implements CandidateDao {

	private final Logger logger = LoggerFactory.getLogger(CandidateDaoJpa.class.getName());

	@Inject
	public CandidateDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		this.logger.debug("Buscando al candidateo con jobOffer {}", jobOffer.getId());
		final Map<String, Object> properties = new HashMap<>();
		properties.put("jobOffer", jobOffer);
		return findByTypedQuery(Candidate.class, "Candidate.findAllByJobOffer", properties, 0, 0);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return findAll(FileType.class);
	}

	@Override
	public Candidate findCandidateAndFiles(final long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		this.logger.debug("Buscando al candidateo con id {}", id);
		final Map<String, Object> properties = new HashMap<>();
		properties.put("id", id);
		return findByTypedQuery(Candidate.class, "Candidate.findCandidateAndFilesById", properties);
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename, final String position,
			final Boolean showDeleted) {
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
		if (showDeleted == null || !showDeleted) {
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