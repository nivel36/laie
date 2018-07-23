package ged.web.view.user;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.Service;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = Role.class)
public class RoleConverter extends AbstractConverter<Role> {

	@Inject
	private RoleService roleService;

	@Override
	protected Service<Role> getService() {
		return this.roleService;
	}

	public void setRoleService(final RoleService roleService) {
		this.roleService = roleService;
	}
}