package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao< User> {

	boolean emailExists(final String email);

	boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(final User user);

	User findUserByUsername(final String username);

	List<User> searchByNameAndSurename(final String name, String surename, final boolean showDeleted);

	boolean usernameExists(final String username);
}