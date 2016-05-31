package ged.ejb.user.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.user.Role;
import ged.ejb.user.RoleDao;
import ged.ejb.user.RoleService;

@Stateless
public class RoleServiceImpl implements RoleService {

	@Inject
	@Repository
	private RoleDao roleDao;

	@Override
	public List<Role> findAllRoles() {
		return this.roleDao.findAllRoles();
	}

	@Override
	public List<Role> findSubordinateRoles(final Role manager) {
		return this.roleDao.findSubordinateRoles(manager.getId());
	}

	@Override
	public boolean isASubordinateRole(final Role manager, final Role role) {
		final List<Role> subordinateRoles = findSubordinateRoles(manager);
		for (final Role subordinateRole : subordinateRoles) {
			if (subordinateRole.equals(role)) {
				return true;
			}
		}
		return false;
	}
}
