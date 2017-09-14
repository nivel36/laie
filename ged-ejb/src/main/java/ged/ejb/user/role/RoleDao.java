package ged.ejb.user.role;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.Dao;

@Local
public interface RoleDao extends Dao<Role> {

	Role findAdmin();

	List<Role> findSubordinateRoles(final Role role);
}