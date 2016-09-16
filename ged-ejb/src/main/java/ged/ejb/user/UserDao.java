package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<Long, User> {

	long countAdminRoles();

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> searchByNameAndSurename(final String name, String surename, String email, final boolean showDeleted);

	boolean usernameExists(final String username);
}