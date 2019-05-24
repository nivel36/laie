package ged.ejb.core.tag;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractDao<Tag> {

	public Tag findByName(String name) {
		Objects.requireNonNull(name, "Name can't be null");
		return this.getPersistenceFacade().findByQuery(Tag.class, "Tag.findByName", map("name", name));
	}

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "label" };
	}
}