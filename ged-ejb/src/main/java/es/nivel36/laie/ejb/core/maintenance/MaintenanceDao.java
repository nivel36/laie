package es.nivel36.laie.ejb.core.maintenance;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class MaintenanceDao extends AbstractDao<AbstractEnumEntity> {

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}
}