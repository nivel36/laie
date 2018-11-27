package ged.ejb.core.maintenance;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDao extends AbstractDao<AbstractEnumEntity> {

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}