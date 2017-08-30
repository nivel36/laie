package ged.ejb.client;

import static ged.ejb.core.model.QueryParameter.with;

import java.lang.invoke.MethodHandles;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDaoJpa extends AbstractDaoJpa<Client> implements ClientDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	public ClientDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public boolean clientExist(final String clientName) {
		Objects.requireNonNull(clientName);
		return (boolean) findByQuery("Client.clientExist", with("name", clientName).parameters());
	}

	@Override
	public Client findByName(final String clientName) {
		Objects.requireNonNull(clientName);
		Client client;
		try {
			client = findByTypedQuery(Client.class, "Client.findByName", with("name", clientName).parameters());
		} catch (final NoResultException e) {
			logger.debug("No client found", e);
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