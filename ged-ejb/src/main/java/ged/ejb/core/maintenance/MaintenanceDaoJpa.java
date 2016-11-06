package ged.ejb.core.maintenance;

import javax.inject.Inject;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDaoJpa extends AbstractDao<Long, EnumEntity> implements MaintenanceDao {

	@Inject
	protected MaintenanceDaoJpa(@Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
	}

	@Override
	public Class<EnumEntity> getClazz() {
		return EnumEntity.class;
	}
}