package ged.ejb.user.role;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private RoleDao roleDao;

	@Override
	public Role findAdmin() {
		logger.debug("Find admin role");
		return this.roleDao.findAdmin();
	}

	@Override
	public List<Role> findAllRoles() {
		logger.debug("Find all roles");
		return this.roleDao.findAll();
	}

	@Override
	public Role findRoleByName(final String roleName) {
		Objects.requireNonNull(roleName);
		logger.debug("Find role by name {}", roleName);
		return this.roleDao.findRoleByName(roleName);
	}

	@Override
	public List<Role> findSubordinateRoles(final Role manager) {
		logger.debug("Find subordinate roles of role {}", manager.getName());
		return this.roleDao.findSubordinateRoles(manager);
	}

	@Override
	protected Dao<Role> getDao() {
		return this.roleDao;
	}

	@Override
	public boolean isASubordinateRole(final Role manager, final Role role) {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(role);
		final List<Role> subordinateRoles = this.findSubordinateRoles(manager);
		for (final Role subordinateRole : subordinateRoles) {
			if (subordinateRole.equals(role)) {
				logger.debug("Role {} is a subordinate role of {}", role.getName(), manager.getName());
				return true;
			}
		}
		logger.debug("Role {} isn't a subordinate role of {}", role.getName(), manager.getName());
		return false;
	}
}
