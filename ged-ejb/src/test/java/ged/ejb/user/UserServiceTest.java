package ged.ejb.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import javax.security.auth.login.LoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleDao;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Nested
	class Login {

		@Test
		public void invalidEmailShoudThrowLoginException() {
			when(UserServiceTest.this.userDao.findUserAndCredential("abel@test.com")).thenReturn(null);
			assertThrows(LoginException.class, () -> {
				UserServiceTest.this.userService.login("abel@test.com", "password");
			});
		}

		@Test
		public void invalidPasswordShoudThrowLoginException() {
			final User user = UserServiceTest.this.mockUser(1L, "abel@test.com", null);
			when(UserServiceTest.this.userDao.findUserAndCredential("abel@test.com")).thenReturn(user);
			assertThrows(LoginException.class, () -> {
				UserServiceTest.this.userService.login("abel@test.com", "pasword");
			});
		}

		@Test
		public void nullEmailShoudThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserServiceTest.this.userService.login(null, "password");
			});
		}

		@Test
		public void nullPasswordShoudThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				UserServiceTest.this.userService.login("abel@test.com", null);
			});
		}

		@Test
		public void validCredentialShoudSaveUser() throws Exception {
			final User user = UserServiceTest.this.mockUser(1L, "abel@test.com", null);
			when(UserServiceTest.this.userDao.findUserAndCredential("abel@test.com")).thenReturn(user);
			when(UserServiceTest.this.userDao.save(user)).thenReturn(user);
			final User savedUser = UserServiceTest.this.userService.login("abel@test.com", "password");
			Assertions.assertTrue(savedUser.getLastConnection() != null);
		}
	}

	@Nested
	class Save {

		@Test
		public void adminWithManagerShouldThrowIllegalStateException() {
			final User manager = UserServiceTest.this.mockUser(2L, "abel@test.com", null);
			final User user = UserServiceTest.this.mockUser(null, "bernat@test.com", manager);
			final Role adminRole = new Role();
			adminRole.setName(Role.ADMIN);
			user.setRole(adminRole);
			user.setManager(user);

			assertThrows(BadManagerException.class, () -> {
				UserServiceTest.this.userService.save(user);
			});
		}

		@Test
		public void adminWithoutManagerShouldReturnUser() {
			final User user = UserServiceTest.this.mockUser(1L, "abel@test.com", null);
			final Role adminRole = new Role();
			adminRole.setName(Role.ADMIN);
			user.setRole(adminRole);
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
	private RoleDao roleDao;

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
		user.setCredential(new Credential(user, "password"));
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.userService = new UserService();
		this.userService.setUserDao(this.userDao);
		this.userService.setRoleDao(this.roleDao);
	}
}