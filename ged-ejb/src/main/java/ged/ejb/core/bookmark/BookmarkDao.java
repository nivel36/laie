package ged.ejb.core.bookmark;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.CrudDao;
import ged.ejb.user.User;

@Local
public interface BookmarkDao extends CrudDao<Long, Bookmark> {

	List<Bookmark> findAllByUser(final User user);

	Bookmark findByUrl(String url);
}