package ged.ejb.user;

import java.time.LocalDate;
import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<User> {

	boolean emailExist(final String email);

	boolean existMoreThanOneAdmin();

	List<User> findSubordinateUsers(final User user);

	User findUserByEmail(final String email);

	List<User> findUsersOffline(final LocalDate start, final LocalDate end);

	List<User> findUsersOnline(final LocalDate start, final LocalDate end);

	long numberOfUsersInTeam(final User user);

	long numberOfUsersOffline(final LocalDate start, final LocalDate end);

	long numberOfUsersOnline(final LocalDate start, final LocalDate end);
}