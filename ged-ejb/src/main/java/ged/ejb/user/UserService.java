package ged.ejb.user;

import java.util.List;

import ged.ejb.core.AuditedService;

public interface UserService extends AuditedService<User> {

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> searchByNameAndSurename(final String name, final String surename);
}