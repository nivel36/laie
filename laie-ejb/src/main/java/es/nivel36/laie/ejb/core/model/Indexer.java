package es.nivel36.laie.ejb.core.model;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class Indexer {

	private @Inject EntityManager em;

	public void index() throws InterruptedException {
		final SearchSession session = Search.session(this.em);
		session.massIndexer().startAndWait();
	}
}
