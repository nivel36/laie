package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
			when(UserDaoTest.this.persistenceFacade.findAll(User.class, Page.ALL_RESULTS)).thenReturn(new ArrayList<User>());

			final List<User> users = UserDaoTest.this.userDao.findAll(Page.ALL_RESULTS);
			assertEquals(0, users.size());
		}
	}

	@Nested
	class FindSubordinateUsers {

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserDaoTest.this.userDao.findSubordinateUsers(null);
			});
		}

		@Test
		public void userWithoutSubordinatesShouldReturnAnEmptyList() {
			final User user = UserDaoTest.this.mockUser(1L, "abel@test.com", null);

			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L),
					Page.ALL_RESULTS)).thenThrow(new NoResultException());

			final List<User> subordinateUsersFromDataBases = UserDaoTest.this.userDao.findSubordinateUsers(user);
			assertEquals(0, subordinateUsersFromDataBases.size());
		}

		@Test
		public void userWithSuborinatesShouldReturnListOfSubordinates() {
			final User manager = UserDaoTest.this.mockUser(1L, "abel@test.com", null);

			final User subordinate = UserDaoTest.this.mockUser(2L, "bernard@test.com", manager);

			final List<User> subordinateUsers = new ArrayList<>();
			subordinateUsers.add(subordinate);

			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L),
					Page.ALL_RESULTS)).thenReturn(subordinateUsers);

			final List<User> subordinateUsersFromDataBase = UserDaoTest.this.userDao.findSubordinateUsers(manager);
			assertEquals(1, subordinateUsersFromDataBase.size());
		}
	}

	@Nested
	class FindUserByEmail {

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserDaoTest.this.userDao.findUserByEmail(null);
			});
		}

		@Test
		public void userFoundShouldReturnUser() {
			final User user = UserDaoTest.this.mockUser(1L, "abel@test.com", null);

			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findByEmail",
					map("email", "abel@test.com"))).thenReturn(user);

			final User userInDataBase = UserDaoTest.this.userDao.findUserByEmail("abel@test.com");
			assertEquals(user, userInDataBase);
		}

		@Test
		public void userNotFoundShouldReturnNull() {
			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findByEmail",
					map("email", "abel@test.com"))).thenThrow(new NoResultException());

			final User userInDataBase = UserDaoTest.this.userDao.findUserByEmail("abel@test.com");
			assertNull(userInDataBase);
		}
	}

	@Nested
	class IsDuplicatedEmail {

		@Test
		public void duplicatedEmailShouldReturnTrue() {
			when(UserDaoTest.this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists",
					map("email", "abel@test.com"))).thenReturn(Boolean.TRUE);

			final boolean result = UserDaoTest.this.userDao.isEmailInUse("abel@test.com");
			assertTrue(result);
		}

		@Test
		public void nonDuplicatedEmailShouldReturnFalse() {
			when(UserDaoTest.this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists",
					map("email", "abel@test.com"))).thenReturn(Boolean.FALSE);

			final boolean result = UserDaoTest.this.userDao.isEmailInUse("abel@test.com");
			assertEquals(Boolean.FALSE, result);
		}

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserDaoTest.this.userDao.isEmailInUse(null);
			});
		}
	}

	@Nested
	class Save {

		public void insertUserWithManagerShouldReturnUser() {
			final User manager = UserDaoTest.this.mockUser(1L, "abel@test.com", null);
			final User subordinate = UserDaoTest.this.mockUser(null, "bernat@test.com", manager);

			when(UserDaoTest.this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists",
					map("email", subordinate.getEmail()))).thenReturn(Boolean.FALSE);

			final User returnedUser = UserDaoTest.this.userDao.save(subordinate);
			assertEquals(subordinate, returnedUser);
		}

		public void insertUserWithoutManagerShouldReturnUser() {
			final User user = UserDaoTest.this.mockUser(null, "abel@test.com", null);

			when(UserDaoTest.this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists",
					map("email", user.getEmail()))).thenReturn(Boolean.FALSE);

			final User returnedUser = UserDaoTest.this.userDao.save(user);
			assertEquals(user, returnedUser);
		}

		@Test
		public void nullUserShouldThrowNulllPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserDaoTest.this.userDao.save(null);
			});
		}

		@Test
		public void updateUserWithDuplicatedEmailShouldThrowDuplicateEmailException() {
			final User user = UserDaoTest.this.mockUser(1L, "abel@test.com", null);
			final User userWithSameEmail = UserDaoTest.this.mockUser(2L, "abel@test.com", null);
			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findByEmail",
					map("email", "abel@test.com"))).thenReturn(userWithSameEmail);

			assertThrows(DuplicateEmailException.class, () -> {
				UserDaoTest.this.userDao.save(user);
			});
		}

		@Test
		public void updateUserWithNewManagerShouldReturnUser() {
			final User userInDatabase = UserDaoTest.this.mockUser(1L, "abel@test.com", null);
			final User manager = UserDaoTest.this.mockUser(2L, "bernat@test.com", null);
			final User userToUpdate = UserDaoTest.this.mockUser(1L, "abel@test.com", manager);
			when(UserDaoTest.this.persistenceFacade.findByQuery(User.class, "User.findByEmail",
					map("email", "abel@test.com"))).thenReturn(userInDatabase);

			when(UserDaoTest.this.persistenceFacade.find(User.class, 1L)).thenReturn(userInDatabase);

			final User updatedUser = UserDaoTest.this.userDao.save(userToUpdate);
			assertNull(updatedUser);
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
