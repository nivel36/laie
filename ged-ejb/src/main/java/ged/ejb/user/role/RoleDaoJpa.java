package ged.ejb.user.role;

import static ged.ejb.core.model.QueryParameter.with;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class RoleDaoJpa extends AbstractDao<Role> implements RoleDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	public RoleDaoJpa(final EntityManager entityManger) {
		super(entityManger);
	}

	@Override
	public List<Role> findSubordinateRoles(final Role role) {
		final List<Role> roles;
		try {
			roles  = findByTypedQuery(Role.class, "Role.findSubordinateRoles", with("id", role.getId()).parameters(), 0, 0);
		} catch (NoResultException e) {
			logger.warn("No subordinate roles for role {}", role.getName());
			return new ArrayList<Role>(); 
		}
		return roles;
	}

	@Override
	protected Class<Role> getType() {
		return Role.class;
	}
}
