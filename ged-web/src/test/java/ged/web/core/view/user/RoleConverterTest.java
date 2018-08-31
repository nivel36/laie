package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.web.view.user.RoleConverter;

@ExtendWith(MockitoExtension.class)
public class RoleConverterTest {

	@Nested
	class GetAsObject {

		@Test
		public void foundRoleShouldReturnRole() {
			final Role roleFromDatabase = mockRole();

			when(userService.findRoleByName(Role.ADMIN)).thenReturn(roleFromDatabase);

			final Role role = roleConverter.getAsObject(null, null, Role.ADMIN);
			assertEquals(roleFromDatabase, role);
		}

		@Test
		public void nullStringShouldReturnNull() {
			final Role role = roleConverter.getAsObject(null, null, null);
			assertNull(role);
		}

		@Test
		public void roleNotFoundShouldReturnNull() {
			when(userService.findRoleByName(Role.USER)).thenReturn(null);

			final Role role = roleConverter.getAsObject(null, null, Role.USER);
			assertNull(role);
		}
	}

	@Nested
	class GetAsString {

		@Test
		public void nullRoleShouldReturnNull() {
			final String value = roleConverter.getAsString(null, null, null);
			assertNull(value);
		}

		@Test
		public void validRoleShouldReturnRoleName() {
			final Role role = mockRole();

			final String value = roleConverter.getAsString(null, null, role);
			assertEquals(Role.ADMIN, value);
		}
	}

	private RoleConverter roleConverter;

	@Mock
	private UserService userService;

	private Role mockRole() {
		final Role role = new Role();
		role.setId(1L);
		role.setName(Role.ADMIN);
		return role;
	}

	@BeforeEach
	public void setUp() {
		this.roleConverter = new RoleConverter();
		this.roleConverter.setUserService(this.userService);
	}
}
