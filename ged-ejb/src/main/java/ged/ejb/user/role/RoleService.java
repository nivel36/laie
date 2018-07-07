package ged.ejb.user.role;

import java.util.List;

import ged.ejb.core.Service;

public interface RoleService extends Service<Role> {

	Role findAdmin();

	List<Role> findAllRoles();

	Role findRoleByName(String roleName);

	List<Role> findSubordinateRoles(final Role manager);

	boolean isASubordinateRole(final Role manager, final Role role);

}