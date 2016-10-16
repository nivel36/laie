package ged.ejb.job.offer;

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

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobOfferDaoJpa extends AbstractDao<Long, JobOffer> implements JobOfferDao {

	private final static Logger logger = Logger.getLogger(JobOfferDaoJpa.class.getName());

	@Inject
	public JobOfferDaoJpa(@Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
	}

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		if (owner == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Buscando todas las ofertas del usuario ", owner.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.persistenceFacade.findByTypedQuery(JobOffer.class, "JobOffer.findAllByOwner", parameters, 10, 0);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.persistenceFacade.findByTypedQuery(JobOffer.class, "JobOffer.findLastJobOffers", parameters, 10, 0);
	}

	@Override
	public Class<JobOffer> getClazz() {
		return JobOffer.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted) {
		final EntityManager em = this.persistenceFacade.getEm();
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(JobOffer.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (clientName != null) {
			bj.must(qb.keyword().onField("client.name").matching(clientName).createQuery());
		}
		if ((showDeleted == null) || !showDeleted) {
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
