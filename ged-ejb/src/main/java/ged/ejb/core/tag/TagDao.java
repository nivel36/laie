package ged.ejb.core.tag;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractDao<Tag> {

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@Override
	public final List<Tag> search(final String searchText, final Page page) {
		return this.getPersistenceFacade().search(page, Tag.class, searchText, "label");
	}
}