package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.Action;

@Local
public interface UserService {

	void deleteAction(final Action action);

	void deleteUser(User user);

	boolean emailExists(final String email);

	List<User> findAll();

	User findById(Long id);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surenames);

	List<User> fullSearch(final String name, final String surename, final String userEmail);

	void insertAction(Action action);

	void insertUser(User user);

	void undeleteUser(final User user);

	User updateUser(User user);
}