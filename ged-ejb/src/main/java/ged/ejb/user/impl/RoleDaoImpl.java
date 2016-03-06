package ged.ejb.user.impl;

import java.util.List;

import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.user.Role;
import ged.ejb.user.RoleDao;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	public List<Role> findAllRoles() {
		return this.persistenceFacade.getAll(Role.class);
	}

}
