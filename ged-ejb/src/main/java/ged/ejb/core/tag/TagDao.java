package ged.ejb.core.tag;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractIndexedDao<Tag> {

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label, "Label can't be null");
		try {
			return this.getPersistenceFacade().findByQuery(Tag.class, "Tag.findByLabel", map("label", label));
		} catch (final NoResultException e) {
			return null;
		}
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