package es.nivel36.laie.ejb.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;
import es.nivel36.laie.ejb.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Nested
	class Save {

		@Test
		public void adminWithManagerShouldThrowIllegalStateException() {
			final User manager = UserServiceTest.this.mockUser(2L, "abel@test.com", null);
			final User user = UserServiceTest.this.mockUser(null, "bernat@test.com", manager);
			user.setRole(Role.ADMIN);
			user.setManager(user);

			assertThrows(BadManagerException.class, () -> {
				UserServiceTest.this.userService.save(user);
			});
		}

		@Test
		public void adminWithoutManagerShouldReturnUser() {
			final User user = UserServiceTest.this.mockUser(1L, "abel@test.com", null);
			user.setRole(Role.ADMIN);
			when(UserServiceTest.this.userDao.save(user)).thenReturn(user);

			final User returnedUser = UserServiceTest.this.userService.save(user);
			assertEquals(returnedUser, user);
		}

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserServiceTest.this.userService.save(null);
			});
		}

		@Test
		public void usersOwnManagerShouldThrowIllegalStateException() {
			final User user = UserServiceTest.this.mockUser(null, "abel@test.com", null);
			user.setManager(user);
			assertThrows(BadManagerException.class, () -> {
				UserServiceTest.this.userService.save(user);
			});
		}
	}

	@Mock
	private UserDao userDao;

	private UserService userService;

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
		this.userService = new UserService();
		this.userService.setUserDao(this.userDao);
	}
}