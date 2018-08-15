package ged.ejb.core.maintenance;

import java.util.List;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDao extends AbstractDao<AbstractEnumEntity> {

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}

	public List<AbstractEnumEntity> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}