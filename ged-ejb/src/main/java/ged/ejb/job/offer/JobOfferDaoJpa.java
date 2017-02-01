package ged.ejb.job.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.client.Client;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobOfferDaoJpa extends AbstractDao<Long, JobOffer> implements JobOfferDao {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferDaoJpa.class.getName());

	@Inject
	public JobOfferDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<JobOffer> findAllByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug( "SELECT all the client offers", client.getName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("client", client);
		return findByTypedQuery(JobOffer.class, "JobOffer.findAllByClient", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug( "Buscando todas las ofertas del usuario ", owner.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return findByTypedQuery(JobOffer.class, "JobOffer.findAllByOwner", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return findByTypedQuery(JobOffer.class, "JobOffer.findLastJobOffers", parameters, 0, 0);
	}

	@Override
	public Class<JobOffer> getType() {
		return JobOffer.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(JobOffer.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (clientName != null) {
			bj.must(qb.keyword().onField("client.name").matching(clientName).createQuery());
		}
		if (showDeleted == null || !showDeleted) {
			bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		}
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), JobOffer.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), JobOffer.class);
		}
		return persistenceQuery.getResultList();
	}
}
