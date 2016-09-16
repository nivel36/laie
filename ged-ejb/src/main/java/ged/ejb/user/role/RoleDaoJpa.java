package ged.ejb.user.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class RoleDaoJpa implements RoleDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public List<Role> findAllRoles() {
		return this.persistenceFacade.findAll(Role.class);
	}

	@Override
	public List<Role> findSubordinateRoles(final Long id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceFacade.findByTypedQuery(Role.class, "Role.findSubordinateRoles", parameters, 0, 0);
	}
}
