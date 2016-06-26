package ged.ejb.user.service;

import java.util.List;

import ged.ejb.user.User;

public interface UserService {

	void deleteUser(User user);

	boolean emailExists(final String email);

	List<User> findAll();

	User findById(Long id);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surenames);

	List<User> fullSearch(final String name, final String surename, final String userEmail);

	void insertUser(User user);

	void undeleteUser(final User user);

	User updateUser(User user);
}