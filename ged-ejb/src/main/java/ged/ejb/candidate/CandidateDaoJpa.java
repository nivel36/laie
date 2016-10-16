package ged.ejb.candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.core.FileType;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class CandidateDaoJpa extends AbstractDao<Long, Candidate> implements CandidateDao {

	private final Logger logger = Logger.getLogger(CandidateDaoJpa.class.getName());

	@Inject
	public CandidateDaoJpa(@Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return this.persistenceFacade.findAll(FileType.class);
	}

	@Override
	public Candidate findCandidateAndFiles(final Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		this.logger.log(Level.FINE, "Buscando al candidateo con id {}", id);
		final Map<String, Object> properties = new HashMap<>();
		properties.put("id", id);
		return this.persistenceFacade.findByTypedQuery(Candidate.class, "Candidate.findCandidateAndFilesById",
				properties);
	}

	@Override
	public Class<Candidate> getClazz() {
		return Candidate.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename, final String position,
			final Boolean showDeleted) {
		final EntityManager em = this.persistenceFacade.getEm();
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
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
		if ((showDeleted == null) || !showDeleted) {
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