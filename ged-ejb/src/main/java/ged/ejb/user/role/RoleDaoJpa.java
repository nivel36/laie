package ged.ejb.user.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class RoleDaoJpa extends AbstractDao< Role> implements RoleDao {

	@Inject
	public RoleDaoJpa(final EntityManager entityManger) {
		super(entityManger);
	}

	@Override
	public List<Role> findSubordinateRoles(final long id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return findByTypedQuery(Role.class, "Role.findSubordinateRoles", parameters, 0, 0);
	}

	@Override
	protected Class<Role> getType() {
		return Role.class;
	}
}
