package ged.ejb.core.tag;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractDao<Tag> {

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label, "Label can't be null");
		return this.getPersistenceFacade().findByQuery(Tag.class, "Tag.findByLabel", map("label", label));
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