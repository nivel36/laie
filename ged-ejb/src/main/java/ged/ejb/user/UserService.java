package ged.ejb.user;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.bookmark.Bookmark;

@Local
public interface UserService {

	public void deleteBookmark(Bookmark bookmark);

	public void deleteUser(User user);

	public List<User> findAll();

	public Bookmark findBookmarkByUrl(String url);

	public User findById(Long id);

	public List<User> findUsers(String name, String surenames);

	public void insertBookmark(Bookmark bookmark);

	public void insertUser(User user);

	public User updateUser(User user);

}