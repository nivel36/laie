package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.core.model.PersistenceFacade;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoJpaTest {

	@Mock
	private PersistenceFacade persistenceFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private UserDaoJpa userDaoJpa;

	@Test
	public void emailExistNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.emailExist(null);
	}

	@Test
	public void emailExistTest() {
		when(this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", "aaron@test.com"))).thenReturn(Boolean.TRUE);
		final boolean result = this.userDaoJpa.emailExist("aaron@test.com");
		Assert.assertTrue(result);
	}

	@Test
	public void existMoreThanOneAdminTest() {
		when(this.persistenceFacade.findByQuery(Boolean.class, "User.existsMoreThanOneAdmin", null)).thenReturn(Boolean.TRUE);
		final boolean result = this.userDaoJpa.existMoreThanOneAdmin();
		Assert.assertTrue(result);
	}

	@Test
	public void findAllTest() {
		when(this.persistenceFacade.findByQuery(User.class, "User.findAll", null, 0, 0)).thenReturn(new ArrayList<User>());
		final List<User> users = this.userDaoJpa.findAll();
		Assert.assertEquals(0, users.size());
	}

	@Test
	public void findSubordinateUsersNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findSubordinateUsers(null);
	}

	@Test
	public void findSubordinateUsersTest() {
		final User user = new User();
		user.setId(1L);
		final User subordinateUser = new User();
		subordinateUser.setId(2L);
		subordinateUser.setManager(user);
		final List<User> subordinateUsers = new ArrayList<>();
		subordinateUsers.add(subordinateUser);
		when(this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0)).thenReturn(subordinateUsers);
		final List<User> subordinateUsersFromDataBase = this.userDaoJpa.findSubordinateUsers(user);
		Assert.assertEquals(1, subordinateUsersFromDataBase.size());
	}

	@Test
	public void findSubordinateUsersWithoutSubordinatesTest() {
		final User user = new User();
		user.setId(1L);
		when(this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0)).thenThrow(new NoResultException());
		final List<User> subordinateUsersFromDataBases = this.userDaoJpa.findSubordinateUsers(user);
		Assert.assertEquals(0, subordinateUsersFromDataBases.size());
	}

	@Test
	public void findUserByEmailNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUserByEmail(null);
	}

	@Test
	public void findUserByEmailTest() {
		final User user = new User();
		user.setEmail("aaron@test.com");
		when(this.persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com"))).thenReturn(user);
		final User userInDataBase = this.userDaoJpa.findUserByEmail("aaron@test.com");
		Assert.assertEquals(user, userInDataBase);
	}

	@Test
	public void findUserByNonExistingEmailTest() {
		when(this.persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com"))).thenThrow(new NoResultException());
		final User userInDataBase = this.userDaoJpa.findUserByEmail("aaron@test.com");
		Assert.assertNull(userInDataBase);
	}

	@Test
	public void findUsersOfflineTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.plusDays(1);
		when(this.persistenceFacade.findByQuery(User.class, "User.findUsersOffline", map("start", start).and("end", end), 0, 0)).thenReturn(new ArrayList<>());
		final List<User> usersInDataBase = this.userDaoJpa.findUsersOffline(start, end);
		Assert.assertEquals(0, usersInDataBase.size());
	}

	@Test
	public void findUsersOfflineWithNullDatesTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOffline(null, LocalDateTime.now());

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOffline(LocalDateTime.now(), null);

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOffline(null, null);
	}

	@Test
	public void findUsersOfflineWithWrongTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.minusDays(1);
		this.thrown.expect(IllegalStateException.class);
		this.userDaoJpa.findUsersOffline(start, end);
	}

	@Test
	public void findUsersOnlineTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.plusDays(1);
		when(this.persistenceFacade.findByQuery(User.class, "User.findUsersOnline", map("start", start).and("end", end), 0, 0)).thenReturn(new ArrayList<>());
		final List<User> usersInDataBase = this.userDaoJpa.findUsersOffline(start, end);
		Assert.assertEquals(0, usersInDataBase.size());
	}

	@Test
	public void findUsersOnlineWithNullDatesTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOnline(null, LocalDateTime.now());

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOnline(LocalDateTime.now(), null);

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUsersOnline(null, null);
	}

	@Test
	public void findUsersOnlineWithWrongDatesTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.minusDays(1);
		this.thrown.expect(IllegalStateException.class);
		this.userDaoJpa.findUsersOnline(start, end);
	}

	@Test
	public void insertNullUserTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.insert(null);
	}

	@Test
	public void insertUserWithManagerTest() {
		final User admin = new User();
		admin.setId(1L);
		final User manager = new User();
		manager.setId(2L);
		manager.setManager(admin);
		final User user = new User();
		user.setManager(manager);

		final UserClosure userClosure = new UserClosure();
		userClosure.setAntecessor(admin);
		userClosure.setDescendant(manager);

		final List<UserClosure> userClosures = new ArrayList<>();
		userClosures.add(userClosure);

		when(this.persistenceFacade.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById", map("id", user.getId()), 0, 0))
				.thenReturn(userClosures);
		this.userDaoJpa.insert(user);
	}

	@Test
	public void insertUserWithoutManagerTest() {
		final User user = new User();
		this.userDaoJpa.insert(user);
	}

	@Test
	public void numberOfUsersInTeamNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersInTeam(null);
	}

	@Test
	public void numberOfUsersInTeamTest() {
		final User user = new User();
		user.setId(1L);
		when(this.persistenceFacade.findByQuery(Long.class, "User.numberOfUsersInTeam", map("id", user.getId()))).thenReturn(1L);
		final long numberOfUsersInTeam = this.userDaoJpa.numberOfUsersInTeam(user);
		Assert.assertEquals(1L, numberOfUsersInTeam);
	}

	@Test
	public void numberOfUsersOfflineNullDatesTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOffline(null, LocalDateTime.now());

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOffline(LocalDateTime.now(), null);

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOffline(null, null);
	}

	@Test
	public void numberOfUsersOfflineTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.plusDays(1);
		when(this.persistenceFacade.findByQuery(Long.class, "User.numberOfUsersOffline", map("start", start).and("end", end))).thenReturn(1L);
		final long numberOfUsersInTeam = this.userDaoJpa.numberOfUsersOffline(start, end);
		Assert.assertEquals(1L, numberOfUsersInTeam);
	}

	@Test
	public void numberOfUsersOfflineWithWrongDatesTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.minusDays(1);
		this.thrown.expect(IllegalStateException.class);
		this.userDaoJpa.numberOfUsersOffline(start, end);
	}

	@Test
	public void numberOfUsersOnlineNullDatesTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOnline(null, LocalDateTime.now());

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOnline(LocalDateTime.now(), null);

		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.numberOfUsersOnline(null, null);
	}

	@Test
	public void numberOfUsersOnlineTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.plusDays(1);
		when(this.persistenceFacade.findByQuery(Long.class, "User.numberOfUsersOnline", map("start", start).and("end", end))).thenReturn(2L);
		final long numberOfUsersInTeam = this.userDaoJpa.numberOfUsersOnline(start, end);
		Assert.assertEquals(2L, numberOfUsersInTeam);
	}

	@Test
	public void numberOfUsersOnlineWithWrongDatesTest() {
		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = start.minusDays(1);
		this.thrown.expect(IllegalStateException.class);
		this.userDaoJpa.numberOfUsersOnline(start, end);
	}

	@Test
	public void searchEmptyTextTest() {
		when(this.persistenceFacade.search(User.class, "", "name", "surname", "email")).thenReturn(new ArrayList<>());
		final List<User> users = this.userDaoJpa.search("");
		Assert.assertEquals(0, users.size());
	}

	@Test
	public void searchNullTest() {
		when(this.persistenceFacade.search(User.class, null, "name", "surname", "email")).thenReturn(new ArrayList<>());
		final List<User> users = this.userDaoJpa.search(null);
		Assert.assertEquals(0, users.size());
	}

	@Test
	public void searchTest() {
		when(this.persistenceFacade.search(User.class, "aaron", "name", "surname", "email")).thenReturn(new ArrayList<>());
		final List<User> users = this.userDaoJpa.search("aaron");
		Assert.assertEquals(0, users.size());
	}

	@Before
	public void setUp() {
		this.userDaoJpa = new UserDaoJpa();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}

	@Test
	public void updateNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.update(null);
	}
}
