package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao< User> {

	Boolean emailExists(final String email);

	Boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(final long id);

	User findUserByUsername(final String username);

	List<User> searchByNameAndSurename(final String name, String surename, final boolean showDeleted);

	Boolean usernameExists(final String username);
}