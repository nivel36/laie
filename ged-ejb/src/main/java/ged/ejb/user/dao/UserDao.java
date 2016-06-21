package ged.ejb.user.dao;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.action.Action;
import ged.ejb.user.User;

@Local
public interface UserDao {

	long countAdminRoles();

	void deleteAction(Action action);

	void deleteUser(User user);

	boolean emailExists(final String email);

	Action findAction(Long auditedId, String entity, User user);

	List<User> findAll();

	User findById(Long id);

	User findByName(String user);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surename);

	List<User> fullSearch(final String name, String surename, final String email, final boolean showDeleted);

	void insertAction(Action action);

	void insertUser(User user);

	User updateUser(User user);

}