package ged.ejb.user;

import java.util.List;

import ged.ejb.core.Service;

public interface UserService extends Service<User> {

	boolean emailExists(String email);

	List<User> findSubordinateUsers(User user);

	User findUserByEmail(String email);

	List<User> findUsersOfflineLastMonth();

	List<User> findUsersOnlineLastWeek();

	long numberOfUsersInTeam(User user);

	long numberOfUsersOfflineLastMonth();

	long numberOfUsersOnlineLastWeek();
}