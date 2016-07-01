package ged.ejb.core.bookmark;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.user.User;

public interface BookmarkService extends Service<Long, Bookmark> {

	void delete(final User user, String entityClass, Long entityId);

	List<Bookmark> findAllByUser(final User user);

	Bookmark findByUrl(final User user, String entityClass, Long entityId);
}