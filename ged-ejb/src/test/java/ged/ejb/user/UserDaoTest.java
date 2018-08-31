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

import ged.ejb.core.model.PersistenceFacade;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

	@Nested
	class FindAll {

		@Test
		public void shouldReturnAList() {
			when(persistenceFacade.findAll(User.class)).thenReturn(new ArrayList<User>());

			final List<User> users = userDaoJpa.findAll();
			assertEquals(0, users.size());
		}
	}

	@Nested
	class FindSubordinateUsers {

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findSubordinateUsers(null);
			});
		}

		@Test
		public void userWithoutSubordinatesShouldReturnAnEmptyList() {
			final User user = new User();
			user.setId(1L);

			when(persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0))
					.thenThrow(new NoResultException());

			final List<User> subordinateUsersFromDataBases = userDaoJpa.findSubordinateUsers(user);
			assertEquals(0, subordinateUsersFromDataBases.size());
		}

		@Test
		public void userWithSuborinatesShouldReturnListOfSubordinates() {
			final User user = new User();
			user.setId(1L);

			final User subordinateUser = new User();
			subordinateUser.setId(2L);
			subordinateUser.setManager(user);

			final List<User> subordinateUsers = new ArrayList<>();
			subordinateUsers.add(subordinateUser);

			when(persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0))
					.thenReturn(subordinateUsers);

			final List<User> subordinateUsersFromDataBase = userDaoJpa.findSubordinateUsers(user);
			assertEquals(1, subordinateUsersFromDataBase.size());
		}
	}

	@Nested
	class FindUserByEmail {

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUserByEmail(null);
			});
		}

		@Test
		public void userFoundShouldReturnUser() {
			final User user = new User();
			user.setEmail("aaron@test.com");

			when(persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com")))
					.thenReturn(user);

			final User userInDataBase = userDaoJpa.findUserByEmail("aaron@test.com");
			assertEquals(user, userInDataBase);
		}

		@Test
		public void userNotFoundShouldReturnNull() {
			when(persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com")))
					.thenThrow(new NoResultException());

			final User userInDataBase = userDaoJpa.findUserByEmail("aaron@test.com");
			assertNull(userInDataBase);
		}
	}

	@Nested
	class FndUsersOffline {

		@Test
		public void nullDatesShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOffline(null, null);
			});
		}

		@Test
		public void nullEndDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOffline(LocalDateTime.now(), null);
			});
		}

		@Test
		public void nullStartDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOffline(null, LocalDateTime.now());
			});
		}

		@Test
		public void shouldReturnAList() {
			final LocalDateTime start = LocalDateTime.now();
			final LocalDateTime end = start.plusDays(1);

			when(persistenceFacade.findByQuery(User.class, "User.findUsersOffline", map("start", start).and("end", end),
					0, 0)).thenReturn(new ArrayList<>());

			final List<User> usersInDataBase = userDaoJpa.findUsersOffline(start, end);
			assertEquals(0, usersInDataBase.size());
		}

		@Test
		public void startDateAfterEndDateShouldThrowIllegalStateException() {
			assertThrows(IllegalStateException.class, () -> {
				final LocalDateTime start = LocalDateTime.now();
				final LocalDateTime end = start.minusDays(1);
				userDaoJpa.findUsersOffline(start, end);
			});
		}
	}

	@Nested
	class FndUsersOnline {

		@Test
		public void nullDatesShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOnline(null, null);
			});
		}

		@Test
		public void nullEndDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOnline(LocalDateTime.now(), null);
			});
		}

		@Test
		public void nullStartDateShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.findUsersOnline(null, LocalDateTime.now());
			});
		}

		@Test
		public void shouldReturnAList() {
			final LocalDateTime start = LocalDateTime.now();
			final LocalDateTime end = start.plusDays(1);

			when(persistenceFacade.findByQuery(User.class, "User.findUsersOnline", map("start", start).and("end", end),
					0, 0)).thenReturn(new ArrayList<>());

			final List<User> usersInDataBase = userDaoJpa.findUsersOnline(start, end);
			assertEquals(0, usersInDataBase.size());
		}

		@Test
		public void startDateAfterEndDateShouldThrowIllegalStateException() {
			assertThrows(IllegalStateException.class, () -> {
				final LocalDateTime start = LocalDateTime.now();
				final LocalDateTime end = start.minusDays(1);
				userDaoJpa.findUsersOnline(start, end);
			});
		}
	}

	@Nested
	class IsDuplicatedEmail {

		@Test
		public void duplicatedEmailShouldReturnTrue() {
			when(persistenceFacade.findByQuery(Boolean.class, "User.existsMoreThanOneAdmin", null))
					.thenReturn(Boolean.TRUE);

			final boolean result = userDaoJpa.existsMoreThanOneAdmin();
			assertTrue(result);
		}

		@Test
		public void nonDuplicatedEmailShouldReturnFalse() {
			when(persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", "aaron@test.com")))
					.thenReturn(Boolean.TRUE);

			final boolean result = userDaoJpa.isDuplicatedEmail("aaron@test.com");
			assertTrue(result);
		}

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.isDuplicatedEmail(null);
			});
		}
	}

	@Nested
	class Save {

		@Test
		public void insertUserWithManagerShouldReturnUser() {
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

			User returnedUser = userDaoJpa.save(user);
			assertEquals(user, returnedUser);
		}

		@Test
		public void insertUserWithoutManagerShouldReturnUser() {
			final User user = new User();
			User returnedUser = userDaoJpa.save(user);
			assertEquals(user, returnedUser);
		}

		@Test
		public void nullUserShouldThrowNulllPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userDaoJpa.save(null);
			});
		}
	}

	@Nested
	class Search {

		@Test
		public void emptyTextShouldReturnList() {
			when(persistenceFacade.search(User.class, "", "name", "surname", "email")).thenReturn(new ArrayList<>());
			final List<User> users = userDaoJpa.search("");
			assertEquals(0, users.size());
		}

		@Test
		public void nullTextShouldReturnList() {
			when(persistenceFacade.search(User.class, null, "name", "surname", "email")).thenReturn(new ArrayList<>());
			
			final List<User> users = userDaoJpa.search(null);
			assertEquals(0, users.size());
		}

		@Test
		public void validTextshouldReturnList() {
			when(persistenceFacade.search(User.class, "aaron", "name", "surname", "email"))
					.thenReturn(new ArrayList<>());
			
			final List<User> users = userDaoJpa.search("aaron");
			assertEquals(0, users.size());
		}
	}

	@Mock
	private PersistenceFacade persistenceFacade;

	private UserDao userDaoJpa;

	@BeforeEach
	public void setUp() {
		this.userDaoJpa = new UserDao();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
