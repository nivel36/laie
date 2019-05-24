package ged.ejb.core.bookmark;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.events.PostDelete;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class BookmarkService extends AbstractService<Bookmark> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private BookmarkDao bookmarkDao;

	public void delete(final User user, final String entityClass, final long entityId) {
		final Bookmark bookmark = this.bookmarkDao.find(user, entityClass, entityId);
		this.delete(bookmark);
	}

	public void deleteIfExists(@Observes @PostDelete final AbstractEntity entity) {
		Objects.requireNonNull(entity);
		final List<Bookmark> bookmarks = this.bookmarkDao.find(entity.getClass().getSimpleName(), entity.getId());
		for (final Bookmark bookmark : bookmarks) {
			this.delete(bookmark);
		}
	}

	public void deleteIfExists(final User user, final String entityClass, final long entityId) {
		try {
			final Bookmark bookmark = this.bookmarkDao.find(user, entityClass, entityId);
			this.delete(bookmark);
		}
		catch (final NoResultException ex) {
			logger.debug("No bookmark find to delete", ex);
		}
	}

	public List<Bookmark> findAllByUser(final User user) {
		return this.bookmarkDao.findAllByUser(user);
	}

	public Bookmark findByUrl(final User user, final String entityClass, final long entityId) {
		return this.bookmarkDao.find(user, entityClass, entityId);
	}

	@Override
	protected AbstractDao<Bookmark> getDao() {
		return this.bookmarkDao;
	}

	public void setBookmarkDao(final BookmarkDao bookmarkDao) {
		this.bookmarkDao = bookmarkDao;
	}
}