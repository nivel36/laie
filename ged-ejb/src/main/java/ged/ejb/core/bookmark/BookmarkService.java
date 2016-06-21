package ged.ejb.core.bookmark;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.CrudService;
import ged.ejb.user.User;

@Local
public interface BookmarkService extends CrudService<Long, Bookmark> {

	List<Bookmark> findAllByUser(final User user);

	Bookmark findByUrl(String url);

	void delete(String url);

}
