package ged.ejb.core.maintenance;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDaoJpa extends AbstractDao<Long, EnumEntity> implements MaintenanceDao {

	@Inject
	protected MaintenanceDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Class<EnumEntity> getType() {
		return EnumEntity.class;
	}
}