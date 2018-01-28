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

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Repository
public class CandidateDaoJpa extends AbstractDaoJpa<Candidate> implements CandidateDao {

	@Inject
	public CandidateDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.findByTypedQuery(Boolean.class, "Candidate.emailExists", with("email", email).parameters());
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final List<Candidate> candidates;
		try {
			candidates = this.findByTypedQuery(Candidate.class, "Candidate.findAllByJobOffer",
					with("jobOffer", jobOffer).parameters(), 0, 0);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
		return candidates;
	}

	@Override
	public List<Tag> findAllTags() {
		return this.findAll(Tag.class);
	}

	@Override
	public Candidate findCandidateAndFiles(final long id) {
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.findByTypedQuery(Candidate.class, "Candidate.findCandidateAndFilesById",
				with("id", id).parameters());
	}

	@Override
	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			throw new IllegalArgumentException("numberOfCandidates: " + numberOfCandidates);
		}
		return this.findByTypedQuery(Candidate.class, "Candidate.findLastAddedCandidates", null, numberOfCandidates,
				null);
	}

	@Override
	public long findNumberOfCandidates() {
		return (long) this.findByQuery("Candidate.numberOfCandidates", null);
	}

	@Override
	public Class<Candidate> getType() {
		return Candidate.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> search(final List<String> searchValues) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		for (final String searchValue : searchValues) {
			if (searchValue == null) {
				continue;
			}
			final BooleanJunction<BooleanJunction> fieldBj = qb.bool();
			fieldBj.should(qb.keyword().onFields("name", "surname", "jobProfile", "tags.label").matching(searchValue)
					.createQuery());
			bj.must(fieldBj.createQuery());
		}
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), Candidate.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), Candidate.class);
		}
		return persistenceQuery.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> searchByNameAndSurname(final String name, final String surname, final String position,
			final boolean showDeleted) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (surname != null) {
			bj.must(qb.keyword().onField("surname").matching(surname).createQuery());
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