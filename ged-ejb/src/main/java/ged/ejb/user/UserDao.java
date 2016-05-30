package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;

@Local
public interface UserDao {

	long countAdminRoles();

	void deleteAction(Action action);

	void deleteBookmark(Bookmark bookmark);

	void deleteUser(User user);

	boolean emailExists(final String email);

	Action findAction(Long auditedId, String entity, User user);

	List<User> findAll();

	Bookmark findBookmark(Long auditedId, String entity, User user);

	Bookmark findBookmarkByUrl(String url);

	User findById(Long id);

	User findByName(String user);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surename);

	List<User> findUserTeam(Long id);

	List<User> fullSearch(final String name, String surename, final String email, final boolean showDeleted);

	void insertAction(Action action);

	void insertBookmark(Bookmark bookmark);

	void insertUser(User user);

	User updateUser(User user);

}