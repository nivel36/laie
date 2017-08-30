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
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class BookmarkServiceImpl extends AbstractService<Bookmark> implements BookmarkService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final BookmarkDao dao;

	@Inject
	public BookmarkServiceImpl(@Repository final BookmarkDao dao) {
		Objects.requireNonNull(dao);
		this.dao = dao;
	}

	@Override
	public void delete(final User user, final String entityClass, final long entityId) {
		final Bookmark bookmark = this.dao.find(user, entityClass, entityId);
		delete(bookmark);
	}

	@Override
	public void deleteIfExists(@Observes @PostDelete final AbstractAuditedEntity entity) {
		Objects.requireNonNull(entity);
		final List<Bookmark> bookmarks = this.dao.find(entity.getClass().getSimpleName(), entity.getId());
		for (final Bookmark bookmark : bookmarks) {
			delete(bookmark);
		}
	}

	@Override
	public void deleteIfExists(final User user, final String entityClass, final long entityId) {
		try {
			final Bookmark bookmark = this.dao.find(user, entityClass, entityId);
			delete(bookmark);
		} catch (final NoResultException ex) {
			logger.debug("No bookmark find to delete", ex);
		}
	}

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		return this.dao.findAllByUser(user);
	}

	@Override
	public Bookmark findByUrl(final User user, final String entityClass, final long entityId) {
		return this.dao.find(user, entityClass, entityId);
	}

	@Override
	protected Dao<Bookmark> getDao() {
		return this.dao;
	}
}