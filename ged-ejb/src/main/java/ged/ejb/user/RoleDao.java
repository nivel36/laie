package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RoleDao {

	List<Role> findAllRoles();

	List<Role> findSubordinateRoles(Long id);

}