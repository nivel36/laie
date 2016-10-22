package ged.ejb.core.bookmark;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.AbstractService;
import ged.ejb.core.events.PostDelete;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class BookmarkServiceImpl extends AbstractService<Long, Bookmark> implements BookmarkService {

	private static final Logger logger = Logger.getLogger(BookmarkServiceImpl.class.getName());

	private final BookmarkDao dao;

	@Inject
	public BookmarkServiceImpl(@Repository final BookmarkDao dao) {
		if (dao == null) {
			throw new NullPointerException();
		}
		this.dao = dao;
	}

	@Override
	public void delete(final User user, final String entityClass, final Long entityId) {
		final Bookmark bookmark = this.dao.find(user, entityClass, entityId);
		delete(bookmark);
	}

	@Override
	public void deleteIfExists(@Observes @PostDelete final AuditedEntity<Long> entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		final List<Bookmark> bookmarks = this.dao.find(entity.getClass().getSimpleName(), entity.getId());
		for (final Bookmark bookmark : bookmarks) {
			delete(bookmark);
		}
	}

	@Override
	public void deleteIfExists(final User user, final String entityClass, final Long entityId) {
		try {
			final Bookmark bookmark = this.dao.find(user, entityClass, entityId);
			delete(bookmark);
		} catch (final NoResultException ex) {
			logger.log(Level.FINE, "No bookmark find to delete", ex);
		}
	}

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		return this.dao.findAllByUser(user);
	}

	@Override
	public Bookmark findByUrl(final User user, final String entityClass, final Long entityId) {
		return this.dao.find(user, entityClass, entityId);
	}

	@Override
	protected Dao<Long, Bookmark> getDao() {
		return this.dao;
	}
}