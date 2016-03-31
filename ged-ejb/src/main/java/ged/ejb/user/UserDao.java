package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.IllegalUserAction;

@Local
public interface UserDao {

	public long countAdminRoles();

	public void deleteAction(Action action);

	public void deleteBookmark(Bookmark bookmark);

	public void deleteUser(User user);

	public Action findAction(Long auditedId, String entity, User user);

	public List<User> findAll();

	public Bookmark findBookmark(Long auditedId, String entity, User user);

	public Bookmark findBookmarkByUrl(String url);

	public User findById(Long id);

	public User findByName(String user);

	public User findUserByUsername(String username);

	public List<User> findUsers(String name, String surename);

	public List<User> fullSearch(final String matching);

	public List<User> fullSearch(final String name, String surename);

	public List<User> fullSearchByName(final String name);

	public List<User> fullSearchBySurename(String surename);

	public void insertAction(Action action) throws IllegalUserAction;

	public void insertBookmark(Bookmark bookmark);

	public void insertUser(User user);

	public User updateUser(User user);

}