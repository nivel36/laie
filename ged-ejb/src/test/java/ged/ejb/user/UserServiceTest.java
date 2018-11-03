package ged.ejb.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
	class Save {

		@Test
		public void adminWithManagerShouldThrowIllegalStateException() {
			assertThrows(BadManagerException.class, () -> {
				final User manager = mockUser(2L, "abel@test.com", null);
				final User user = mockUser(null, "bernat@test.com", manager);
				final Role adminRole = new Role();
				adminRole.setName(Role.ADMIN);
				user.setRole(adminRole);
				user.setManager(user);

				userService.save(user);
			});
		}

		@Test
		public void adminWithoutManagerShouldReturnUser() {
			final User user = mockUser(1L, "abel@test.com", null);
			final Role adminRole = new Role();
			adminRole.setName(Role.ADMIN);
			user.setRole(adminRole);
			when(userDao.save(user)).thenReturn(user);

			final User returnedUser = userService.save(user);
			assertEquals(returnedUser, user);
		}

		@Test
		public void nullUserShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				userService.save(null);
			});
		}

		@Test
		public void usersOwnManagerShouldThrowIllegalStateException() {
			assertThrows(BadManagerException.class, () -> {
				final User user = mockUser(null, "abel@test.com", null);
				user.setManager(user);
				userService.save(user);
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
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.userService = new UserService();
		this.userService.setUserDao(userDao);
		this.userService.setRoleDao(roleDao);
	}
}