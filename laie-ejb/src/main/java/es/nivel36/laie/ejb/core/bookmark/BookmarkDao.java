package es.nivel36.laie.ejb.core.bookmark;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class BookmarkDao extends AbstractDao {

	public void insert(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.em.persist(bookmark);
	}

	public void delete(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.delete(Bookmark.class, bookmark);
	}

	public List<Bookmark> findByUserUid(String userUid) {
		Objects.requireNonNull(userUid);
		final String namedQuery = "Bookmark.findByUserUid";
		final Parameters parameters = map("userUid", userUid);
		return this.findByQuery(Bookmark.class, namedQuery, parameters, Page.ALL_RESULTS);
	}
}
