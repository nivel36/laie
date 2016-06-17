package ged.ejb.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ged.ejb.core.AbstractDao;
import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.user.BookmarkDao;
import ged.ejb.user.User;

@Repository
public class BookmarkDaoImpl extends AbstractDao<Long, Bookmark> implements BookmarkDao {

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.getByTypedQuery(getClazz(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Bookmark findByUrl(final String url) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("url", url);
		return this.persistenceFacade.getByTypedQuerySingleResult(getClazz(), "Bookmark.findByUrl", parameters);
	}

	@Override
	public Class<Bookmark> getClazz() {
		return Bookmark.class;
	}
}