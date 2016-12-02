package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<Long, User> {

	Boolean emailExists(final String email);

	Boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(final Long id);

	User findUserByUsername(final String username);

	List<User> searchByNameAndSurename(final String name, String surename, final boolean showDeleted);

	Boolean usernameExists(final String username);
}