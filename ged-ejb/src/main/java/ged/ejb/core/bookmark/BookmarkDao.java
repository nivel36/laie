package ged.ejb.core.bookmark;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface BookmarkDao extends Dao<Bookmark> {

	List<Bookmark> find(String entityClass, long entityId);

	Bookmark find(User user, String entityClass, long entityId);

	List<Bookmark> findAllByUser(final User user);
}