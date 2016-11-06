package ged.ejb.core.maintenance;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class MaintenanceServiceImpl extends AbstractService<Long, EnumEntity> implements MaintenanceService {

	private final MaintenanceDao maintenanceDao;

	@Inject
	public MaintenanceServiceImpl(@Repository final MaintenanceDao maintenanceDao) {
		Objects.requireNonNull(maintenanceDao);
		this.maintenanceDao = maintenanceDao;
	}

	@Override
	protected Dao<Long, EnumEntity> getDao() {
		return this.maintenanceDao;
	}
}
