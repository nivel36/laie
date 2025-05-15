package es.nivel36.laie.ejb.core.bookmark;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * Provides methods for managing bookmarks associated with users.
 *
 * @see Bookmark
 */
@ApplicationScoped
public class BookmarkDao extends AbstractDao {

	/**
	 * Finds a bookmark by its URL.
	 *
	 * @param url the URL of the bookmark to retrieve; must not be null
	 * @return the bookmark entity with the given URL, or null if no matching
	 *         bookmark exists
	 * @throws NullPointerException if the provided URL is null
	 */
	public Bookmark findBookmarkByUrl(final String url) {
		Objects.requireNonNull(url);
		try {
			final String jpql = """
					SELECT b
					FROM Bookmark b
					WHERE b.url = :url
					""";
			final TypedQuery<Bookmark> query = this.em.createQuery(jpql, Bookmark.class);
			query.setParameter("url", url);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}
}
