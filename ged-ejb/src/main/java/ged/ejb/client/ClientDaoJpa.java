package ged.ejb.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDaoJpa extends AbstractDao<Long, Client> implements ClientDao {

	private final Logger logger = LoggerFactory.getLogger(ClientDaoJpa.class.getName());

	@Inject
	public ClientDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Client findByName(final String clientName) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", clientName);
		Client client;
		try {
			client = findByTypedQuery(Client.class, "Client.findByName", parameters);
		} catch (final NoResultException e) {
			this.logger.debug( "No client found with that name", e);
			client = null;
		}
		return client;
	}

	@Override
	public Class<Client> getType() {
		return Client.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Client> searchByName(final String clientName, final boolean showDeleted) {
		this.logger.debug( "SEARCH client by name {} ", clientName);
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Client.class)
				.get();

		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (clientName != null) {
			bj.must(qb.keyword().onField("name").matching(clientName).createQuery());
		}
		if (!showDeleted) {
			bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		}
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), Client.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), Client.class);
		}
		return persistenceQuery.getResultList();
	}
}