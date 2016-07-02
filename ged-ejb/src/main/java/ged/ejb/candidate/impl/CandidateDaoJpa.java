package ged.ejb.candidate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateDao;
import ged.ejb.core.FileType;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;

@Repository
public class CandidateDaoJpa extends AbstractDao<Long, Candidate> implements CandidateDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public List<FileType> findAllFileTypes() {
		return this.persistenceFacade.findAll(FileType.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Candidate> findByNameAndSurename(final String name, final String surename, final boolean showDeleted) {
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
		if (!showDeleted) {
			bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		}
		Query persistenceQuery = null;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), Candidate.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), Candidate.class);
		}
		final List<Candidate> result = persistenceQuery.getResultList();
		return result;
	}

	@Override
	public Curriculum findCurriculumByCandidateId(final long id) {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("id", id);
		final Curriculum curriculum = this.persistenceFacade.findByTypedQuery(Curriculum.class,
				"Candidate.findCurriculumByCandidateId", properties);
		return curriculum;
	}

	@Override
	public Class<Candidate> getClazz() {
		return Candidate.class;
	}
}