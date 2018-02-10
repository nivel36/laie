package ged.ejb.user;

import java.util.List;

import ged.ejb.core.Service;

public interface UserService extends Service<User> {

	boolean emailExists(final String email);

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> findUsersOfflineLastMonth();

	List<User> findUsersOnlineLastWeek();

	long numberOfUsersInTeam(final User user);

	long numberOfUsersOfflineLastMonth();

	long numberOfUsersOnlineLastWeek();
}