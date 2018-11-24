package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.core.model.Page;
import ged.ejb.core.model.PersistenceFacade;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

	@Nested
	class FindAll {

		@Test
		public void shouldReturnAList() {
			when(persistenceFacade.findAll(User.class)).thenReturn(new ArrayList<User>());

			final List<User> users = userDao.findAll();
			assertEquals(0, users.size());
		}
	}

	@Nested
	class FindSubordinateUsers {

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findSubordinateUsers(null);
			});
		}

		@Test
		public void userWithoutSubordinatesShouldReturnAnEmptyList() {
			final User user = mockUser(1L, "abel@test.com", null);

			when(persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), Page.ALL)).thenThrow(new NoResultException());

			final List<User> subordinateUsersFromDataBases = userDao.findSubordinateUsers(user);
			assertEquals(0, subordinateUsersFromDataBases.size());
		}

		@Test
		public void userWithSuborinatesShouldReturnListOfSubordinates() {
			final User manager = mockUser(1L, "abel@test.com", null);

			final User subordinate = mockUser(2L, "bernard@test.com", manager);

			final List<User> subordinateUsers = new ArrayList<>();
			subordinateUsers.add(subordinate);

			when(persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), Page.ALL)).thenReturn(subordinateUsers);

			final List<User> subordinateUsersFromDataBase = userDao.findSubordinateUsers(manager);
			assertEquals(1, subordinateUsersFromDataBase.size());
		}
	}

	@Nested
	class FindUserByEmail {

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUserByEmail(null);
			});
		}

		@Test
		public void userFoundShouldReturnUser() {
			final User user = mockUser(1L, "abel@test.com", null);

			when(persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "abel@test.com"))).thenReturn(user);

			final User userInDataBase = userDao.findUserByEmail("abel@test.com");
			assertEquals(user, userInDataBase);
		}

		@Test
		public void userNotFoundShouldReturnNull() {
			when(persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "abel@test.com"))).thenThrow(new NoResultException());

			final User userInDataBase = userDao.findUserByEmail("abel@test.com");
			assertNull(userInDataBase);
		}
	}

	@Nested
	class FndUsersOffline {

		@Test
		public void nullDatesShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOffline(null, null);
			});
		}

		@Test
		public void nullEndDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOffline(LocalDateTime.now(), null);
			});
		}

		@Test
		public void nullStartDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOffline(null, LocalDateTime.now());
			});
		}

		@Test
		public void shouldReturnAList() {
			final LocalDateTime start = LocalDateTime.now();
			final LocalDateTime end = start.plusDays(1);

			when(persistenceFacade.findByQuery(User.class, "User.findUsersOffline", map("start", start).and("end", end), Page.ALL))
					.thenReturn(new ArrayList<>());

			final List<User> usersInDataBase = userDao.findUsersOffline(start, end);
			assertEquals(0, usersInDataBase.size());
		}

		@Test
		public void startDateAfterEndDateShouldThrowIllegalStateException() {
			assertThrows(IllegalStateException.class, () -> {
				final LocalDateTime start = LocalDateTime.now();
				final LocalDateTime end = start.minusDays(1);
				userDao.findUsersOffline(start, end);
			});
		}
	}

	@Nested
	class FndUsersOnline {

		@Test
		public void nullDatesShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOnline(null, null);
			});
		}

		@Test
		public void nullEndDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOnline(LocalDateTime.now(), null);
			});
		}

		@Test
		public void nullStartDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.findUsersOnline(null, LocalDateTime.now());
			});
		}

		@Test
		public void shouldReturnAList() {
			final LocalDateTime start = LocalDateTime.now();
			final LocalDateTime end = start.plusDays(1);

			when(persistenceFacade.findByQuery(User.class, "User.findUsersOnline", map("start", start).and("end", end), Page.ALL))
					.thenReturn(new ArrayList<>());

			final List<User> usersInDataBase = userDao.findUsersOnline(start, end);
			assertEquals(0, usersInDataBase.size());
		}

		@Test
		public void startDateAfterEndDateShouldThrowIllegalStateException() {
			assertThrows(IllegalStateException.class, () -> {
				final LocalDateTime start = LocalDateTime.now();
				final LocalDateTime end = start.minusDays(1);
				userDao.findUsersOnline(start, end);
			});
		}
	}

	@Nested
	class IsDuplicatedEmail {

		@Test
		public void duplicatedEmailShouldReturnTrue() {
			when(persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", "abel@test.com"))).thenReturn(Boolean.TRUE);

			final boolean result = userDao.isEmailInUse("abel@test.com");
			assertTrue(result);
		}

		@Test
		public void nonDuplicatedEmailShouldReturnFalse() {
			when(persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", "abel@test.com"))).thenReturn(Boolean.FALSE);

			final boolean result = userDao.isEmailInUse("abel@test.com");
			assertEquals(Boolean.FALSE, result);
		}

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.isEmailInUse(null);
			});
		}
	}

	@Nested
	class Save {

		@Test
		public void insertUserWithManagerShouldReturnUser() {
			final User manager = mockUser(1L, "abel@test.com", null);
			final User subordinate = mockUser(null, "bernat@test.com", manager);

			when(persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", subordinate.getEmail()))).thenReturn(Boolean.FALSE);

			final User returnedUser = userDao.save(subordinate);
			assertEquals(subordinate, returnedUser);
		}

		@Test
		public void insertUserWithoutManagerShouldReturnUser() {
			final User user = mockUser(null, "abel@test.com", null);

			when(persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", user.getEmail()))).thenReturn(Boolean.FALSE);

			final User returnedUser = userDao.save(user);
			assertEquals(user, returnedUser);
		}

		@Test
		public void nullUserShouldThrowNulllPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDao.save(null);
			});
		}
	}

	@Nested
	class Search {

		@Test
		public void emptyTextShouldReturnList() {
			when(persistenceFacade.search(User.class, "", "name", "surname", "email")).thenReturn(new ArrayList<>());
			final List<User> users = userDao.search("");
			assertEquals(0, users.size());
		}

		@Test
		public void nullTextShouldReturnList() {
			when(persistenceFacade.search(User.class, null, "name", "surname", "email")).thenReturn(new ArrayList<>());

			final List<User> users = userDao.search(null);
			assertEquals(0, users.size());
		}

		@Test
		public void validTextshouldReturnList() {
			when(persistenceFacade.search(User.class, "aaron", "name", "surname", "email")).thenReturn(new ArrayList<>());

			final List<User> users = userDao.search("aaron");
			assertEquals(0, users.size());
		}
	}

	@Mock
	private PersistenceFacade persistenceFacade;

	private UserDao userDao;

	private User mockUser(final Long id, final String email, final User manager) {
		final User user = new User();
		if (id != null) {
			user.setId(id);
		}
		user.setEmail(email);
		user.setManager(manager);
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.userDao = new UserDao();
		this.userDao.setPersistenceFacade(this.persistenceFacade);
	}
}
