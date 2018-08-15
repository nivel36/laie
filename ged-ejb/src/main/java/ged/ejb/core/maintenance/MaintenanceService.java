package ged.ejb.core.maintenance;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class MaintenanceService extends AbstractService<AbstractEnumEntity> {

	@Inject
	@Repository
	private MaintenanceDao maintenanceDao;

	@Override
	protected AbstractDao<AbstractEnumEntity> getDao() {
		return this.maintenanceDao;
	}

	public void setMaintenanceDao(final MaintenanceDao maintenanceDao) {
		this.maintenanceDao = maintenanceDao;
	}
}
