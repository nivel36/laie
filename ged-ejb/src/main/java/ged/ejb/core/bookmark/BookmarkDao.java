package ged.ejb.core.bookmark;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class BookmarkDao extends AbstractDao<Bookmark> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public List<Bookmark> find(final String entityClass, final long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return this.findByQuery(this.getType(), "Bookmark.findAllByClassAndId", parameters, 0, 0);
	}

	public Bookmark find(final User user, final String entityClass, final long entityId) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		parameters.put("entityClass", entityClass);
		parameters.put("entityId", entityId);
		return this.findByQuery(this.getType(), "Bookmark.find", parameters);
	}

	public List<Bookmark> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Buscando todos los Bookmarks del usuario {}", user.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		return this.findByQuery(this.getType(), "Bookmark.findAllByUser", parameters, 0, 0);
	}

	@Override
	public Class<Bookmark> getType() {
		return Bookmark.class;
	}

	@Override
	public List<Bookmark> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}