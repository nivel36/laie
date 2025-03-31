package es.nivel36.laie.ejb.core.tag;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class TagService {

	private static final Logger logger = LoggerFactory.getLogger(TagService.class);

	private @Inject TagDao tagDao;

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label);
		logger.debug("Retrieving tag by label {}", label);
		return tagDao.findByLabel(label);
	}

	public SearchResult<Tag> search(final String searchText, final Page page) {
		return search(searchText, page, null, null);
	}

	public SearchResult<Tag> search(final String searchText, final Page page, final SortField sortOrder,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Search {}, offset {} with limit of {}", searchText, page.getOffset(), page.getLimit());
		return this.tagDao.search(searchText, page, sortOrder, searchFacets);
	}

	public void setTagDao(final TagDao tagDao) {
		this.tagDao = Objects.requireNonNull(tagDao);
	}
}
