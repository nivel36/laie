package ged.ejb.core.tag;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractDao<Tag> {

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "label" };
	}
}