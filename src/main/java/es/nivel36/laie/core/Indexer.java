package es.nivel36.laie.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class Indexer {

	@PersistenceContext
	private EntityManager em;

	public void index() throws InterruptedException {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.em);
		fullTextEntityManager.createIndexer().startAndWait();
	}
}
