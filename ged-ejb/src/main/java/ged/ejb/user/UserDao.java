package ged.ejb.user;

import java.time.LocalDateTime;
import java.util.List;

import ged.ejb.core.model.Dao;

public interface UserDao extends Dao<User> {

	boolean emailExist(String email);

	boolean existMoreThanOneAdmin();

	List<User> findSubordinateUsers(User user);

	User findUserByEmail(String email);

	List<User> findUsersOffline(LocalDateTime start, LocalDateTime end);

	List<User> findUsersOnline(LocalDateTime start, LocalDateTime end);

	long numberOfUsersInTeam(User user);

	long numberOfUsersOffline(LocalDateTime start, LocalDateTime end);

	long numberOfUsersOnline(LocalDateTime start, LocalDateTime end);
}