package ged.ejb.user.role;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Repository;

@Stateless
public class RoleServiceImpl implements RoleService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private RoleDao roleDao;

	@Override
	public List<Role> findAllRoles() {
		return this.roleDao.findAll();
	}

	@Override
	public List<Role> findSubordinateRoles(final Role manager) {
		logger.debug("Find subordinate roles of role {}", manager.getName());
		return this.roleDao.findSubordinateRoles(manager);
	}

	@Override
	public boolean isASubordinateRole(final Role manager, final Role role) {
		final List<Role> subordinateRoles = findSubordinateRoles(manager);
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
