package ged.ejb.user;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.user.role.Role;

public interface UserService extends AuditedService<User> {

	User create(final String name, final String surename, final String email, final Role role, final User manager);

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> searchByNameAndSurename(final String name, final String surename);
}