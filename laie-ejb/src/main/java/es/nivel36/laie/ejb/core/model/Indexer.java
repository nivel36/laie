package es.nivel36.laie.ejb.core.model;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class Indexer {

	@Inject
	private EntityManager em;

	public void index() throws InterruptedException {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.em);
		fullTextEntityManager.createIndexer().startAndWait();
	}
}
