package ged.ejb.core.bookmark;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

public interface BookmarkService extends Service<Bookmark> {

	void delete(final User user, String entityClass, long entityId);

	void deleteIfExists(final AuditedEntity entity);

	void deleteIfExists(final User user, String entityClass, long entityId);

	List<Bookmark> findAllByUser(final User user);

	Bookmark findByUrl(final User user, String entityClass, long entityId);
}