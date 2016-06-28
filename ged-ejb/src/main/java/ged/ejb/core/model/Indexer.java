package ged.ejb.core.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

@ApplicationScoped
public class Indexer {

	@Inject
	private EntityManager em;

	public void index() throws InterruptedException {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.em);
		fullTextEntityManager.createIndexer().startAndWait();
	}
}
