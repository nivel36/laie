package ged.ejb.core.user;

import java.util.List;

import javax.ejb.Stateless;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class RoleService extends GenericServiceImpl {

	public List<Role> getAll() {
		return getAll(Role.class);
	}
}
