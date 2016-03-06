package ged.ejb.user.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.user.Role;
import ged.ejb.user.RoleService;

@Stateless
public class RoleServiceImpl implements RoleService {

	@Inject
	@Repository
	private RoleDaoImpl roleDao;

	@Override
	public List<Role> findAllRoles() {
		return this.roleDao.findAllRoles();
	}
}
