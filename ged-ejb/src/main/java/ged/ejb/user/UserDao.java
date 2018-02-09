package ged.ejb.user;

import java.util.Date;
import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<User> {

	boolean emailExists(final String email);

	boolean existsMoreThanOneAdmin();

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> findUsersOffline(final Date start, final Date end);

	List<User> findUsersOnline(final Date start, final Date end);

	long numberOfUsersInTeam(final User user);

	long numberOfUsersOffline(final Date start, final Date end);

	long numberOfUsersOnline(final Date start, final Date end);
}