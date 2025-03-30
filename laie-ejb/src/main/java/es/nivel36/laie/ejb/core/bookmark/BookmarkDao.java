package es.nivel36.laie.ejb.core.bookmark;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.persistence.NoResultException;

public class BookmarkDao extends AbstractDao {

	public Bookmark findBookmarkByUrl(final String url) {
		Objects.requireNonNull(url);
		try {
			final String namedQuery = "Bookmark.findByUrl";
			final Parameters parameters = map("url", url);
			return this.findByQuery(Bookmark.class, namedQuery, parameters);
		} catch (final NoResultException e) {
			return null;
		}
	}
}
