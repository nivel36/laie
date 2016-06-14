package ged.ejb.user.impl;

import java.util.HashMap;
import java.util.Map;

import ged.ejb.core.AbstractDao;
import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.user.BookmarkDao;

@Repository
public class BookmarkDaoImpl extends AbstractDao<Long, Bookmark> implements BookmarkDao {

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