package es.nivel36.laie.ejb.core.model;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class Indexer {

	@Inject
	private EntityManager em;

	public void index() throws InterruptedException {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.em);
		fullTextEntityManager.createIndexer().startAndWait();
	}
}
