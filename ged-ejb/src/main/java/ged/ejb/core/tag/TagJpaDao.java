package ged.ejb.core.tag;

import java.util.List;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class TagJpaDao extends AbstractDaoJpa<Tag> implements TagDao {

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@Override
	public final List<Tag> search(final String searchText) {
		return this.getPf().search(Tag.class, searchText, "label");
	}
}