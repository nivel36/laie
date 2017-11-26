package ged.ejb.core.tag;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class TagJpaDao extends AbstractDaoJpa<Tag> implements TagDao {

	@Inject
	public TagJpaDao(final EntityManager em) {
		super(em);
	}

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public final List<Tag> searchByLabel(final String label) {
		Objects.requireNonNull(label);
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Tag.class).get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		bj.must(qb.keyword().onField("label").matching(label).createQuery());
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), Candidate.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), Candidate.class);
		}
		return persistenceQuery.getResultList();
	}
}