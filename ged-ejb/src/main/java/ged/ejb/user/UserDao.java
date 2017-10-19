package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<User> {

	boolean emailExists(final String email);

	boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> search(final String query, final boolean showDeleted);

	List<User> searchByNameAndSurename(final String name, String surename, final boolean showDeleted);
}