package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RoleService {

	List<Role> findAllRoles();

	List<Role> findSubordinateRoles(final Role manager);

	boolean isASubordinateRole(final Role manager, final Role role);

}