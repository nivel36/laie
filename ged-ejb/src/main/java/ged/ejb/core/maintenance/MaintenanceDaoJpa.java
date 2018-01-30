package ged.ejb.core.maintenance;

import java.util.List;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDaoJpa extends AbstractDaoJpa<AbstractEnumEntity> implements MaintenanceDao {

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}

	@Override
	public List<AbstractEnumEntity> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}