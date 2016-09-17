package ged.ejb.core.bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class BookmarkDaoJpa extends AbstractDao<Long, Bookmark> implements BookmarkDao {

	private final Logger logger;

	@Inject
	public BookmarkDaoJpa(final Logger logger, @Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
		this.logger = logger;
	}

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
		if (user == null) {
			throw new NullPointerException();
		}
		this.logger.log(Level.FINE, "Buscando todos los Bookmarks del usuario ", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.persistenceFacade.findByTypedQuery(getClazz(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Class<Bookmark> getClazz() {
		return Bookmark.class;
	}
}