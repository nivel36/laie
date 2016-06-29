package ged.ejb.core.bookmark;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.user.User;

public interface BookmarkDao extends Dao<Long, Bookmark> {

	List<Bookmark> findAllByUser(final User user);

	Bookmark findByUrl(String url);
}