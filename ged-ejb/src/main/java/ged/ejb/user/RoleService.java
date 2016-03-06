package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RoleService {

	public List<Role> findAllRoles();

}