package ged.ejb.core.bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class BookmarkDaoJpa extends AbstractDao<Long, Bookmark> implements BookmarkDao {

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Bookmark findByUrl(final String url) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("url", url);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.findByUrl", parameters);
	}

	@Override
	public Class<Bookmark> getClazz() {
		return Bookmark.class;
	}
}