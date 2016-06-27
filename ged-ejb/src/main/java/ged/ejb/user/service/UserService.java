package ged.ejb.user.service;

import java.util.List;

import ged.ejb.core.AuditedCrudService;
import ged.ejb.user.User;

public interface UserService extends AuditedCrudService<User> {

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> findUsers(String name, String surenames);

	List<User> fullSearch(final String name, final String surename, final String userEmail);

}