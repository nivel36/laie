package ged.ejb.user.dao;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.user.Role;

@Local
public interface RoleDao {

	List<Role> findAllRoles();

	List<Role> findSubordinateRoles(Long id);

}