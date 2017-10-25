package ged.ejb.user;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.user.role.Role;

public interface UserService extends Service<User> {

	User create(final String name, final String surename, final String email, final Role role, final User manager);

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> findUsersOfflineLastMonth();

	List<User> findUsersOnlineLastWeek();

	long numberOfUsersOfflineLastMonth();

	long numberOfUsersOnlineLastWeek();

	List<User> search(final String query);

	List<User> searchByNameAndSurename(final String name, final String surename);
}