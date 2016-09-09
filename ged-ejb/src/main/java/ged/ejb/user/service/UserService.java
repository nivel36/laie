package ged.ejb.user.service;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.user.User;

public interface UserService extends AuditedService<User> {

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> searchByNameAndSurename(final String name, final String surename, final String email);

	boolean usernameExists(final String username);
}