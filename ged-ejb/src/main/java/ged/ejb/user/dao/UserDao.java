package ged.ejb.user.dao;

import java.util.List;

import ged.ejb.core.model.CrudDao;
import ged.ejb.user.User;

public interface UserDao extends CrudDao<Long, User> {

	long countAdminRoles();

	boolean emailExists(final String email);

	User findByName(String user);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surename);

	List<User> fullSearch(final String name, String surename, final String email, final boolean showDeleted);
}