package ged.ejb.core.maintenance;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDao extends AbstractDao<AbstractEnumEntity> {

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}

	@Override
	public List<AbstractEnumEntity> search(final String searchText, final Page page) {
		throw new UnsupportedOperationException();
	}
}