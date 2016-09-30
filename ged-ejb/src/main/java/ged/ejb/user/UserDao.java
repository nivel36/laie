package ged.ejb.user;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<Long, User> {

	Boolean emailExists(final String email);

	Boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> searchByNameAndSurename(final String name, String surename, String email, final boolean showDeleted);

	Boolean usernameExists(final String username);
}