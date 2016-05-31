package ged.ejb.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public List<Role> findAllRoles() {
		return this.persistenceFacade.getAll(Role.class);
	}

	@Override
	public List<Role> findSubordinateRoles(final Long id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceFacade.getByTypedQuery(Role.class, "Role.findSubordinateRoles", parameters, 0, 0);
	}
}
