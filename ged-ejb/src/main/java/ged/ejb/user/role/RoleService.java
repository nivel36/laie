package ged.ejb.user.role;

import java.util.List;

public interface RoleService {

	List<Role> findAllRoles();

	List<Role> findSubordinateRoles(final Role manager);

	boolean isASubordinateRole(final Role manager, final Role role);

}