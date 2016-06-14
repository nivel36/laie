package ged.ejb.user;

import javax.ejb.Local;

import ged.ejb.core.CrudService;
import ged.ejb.core.bookmark.Bookmark;

@Local
public interface BookmarkService extends CrudService<Long, Bookmark> {

	Bookmark findByUrl(String url);

}
