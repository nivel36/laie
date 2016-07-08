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
	public List<Bookmark> find(final String entityClass, final Long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.findAllByClassAndId", parameters, 0, 0);
	}

	@Override
	public Bookmark find(final User user, final String entityClass, final Long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.find", parameters);
	}

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Class<Bookmark> getClazz() {
		return Bookmark.class;
	}
}