package ged.ejb.core.bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class BookmarkDaoJpa extends AbstractDao<Long, Bookmark> implements BookmarkDao {

	private static final Logger logger = Logger.getLogger(BookmarkDaoJpa.class.getName());

	@Inject
	public BookmarkDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Bookmark> find(final String entityClass, final Long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return findByTypedQuery(getType(), "Bookmark.findAllByClassAndId", parameters, 0, 0);
	}

	@Override
	public Bookmark find(final User user, final String entityClass, final Long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return findByTypedQuery(getType(), "Bookmark.find", parameters);
	}

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.log(Level.FINE, "Buscando todos los Bookmarks del usuario ", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return findByTypedQuery(getType(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Class<Bookmark> getType() {
		return Bookmark.class;
	}
}