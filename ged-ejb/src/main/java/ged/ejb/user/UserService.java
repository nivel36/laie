package ged.ejb.user;

import java.util.List;

import ged.ejb.core.AuditedService;

public interface UserService extends AuditedService<Long, User> {

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(Long id);

	User findUserByUsername(String username);

	List<User> searchByNameAndSurename(final String name, final String surename);

	boolean usernameExists(final String username);
}