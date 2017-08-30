package ged.ejb.core.maintenance;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class MaintenanceDaoJpa extends AbstractDaoJpa<AbstractEnumEntity> implements MaintenanceDao<AbstractEnumEntity> {

	@Inject
	protected MaintenanceDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Class<AbstractEnumEntity> getType() {
		return AbstractEnumEntity.class;
	}
}