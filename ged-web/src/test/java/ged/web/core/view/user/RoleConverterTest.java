package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.web.view.user.RoleConverter;

@ExtendWith(MockitoExtension.class)
public class RoleConverterTest {

	private RoleConverter roleConverter;

	@Mock
	private UserService userService;

	@Test
	public void getAsObjectNotFoundTest() {
		final Role roleFromDatabase = new Role();
		roleFromDatabase.setId(1L);
		roleFromDatabase.setName("ADMIN");
		when(this.userService.findRoleByName("ADMIN")).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "USER");
		assertNull(role);
	}

	@Test
	public void getAsObjectNullTest() {
		final Role role = this.roleConverter.getAsObject(null, null, null);
		assertNull(role);
	}

	@Test
	public void getAsObjectTest() {
		final Role roleFromDatabase = new Role();
		roleFromDatabase.setId(1L);
		roleFromDatabase.setName("ADMIN");
		when(this.userService.findRoleByName("ADMIN")).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "ADMIN");
		assertEquals(roleFromDatabase, role);
	}

	@Test
	public void getAsStringNullTest() {
		final String value = this.roleConverter.getAsString(null, null, null);
		assertNull(value);
	}

	@Test
	public void getAsStringValidTest() {
		final Role role = new Role();
		role.setName("ADMIN");
		final String value = this.roleConverter.getAsString(null, null, role);
		assertEquals("ADMIN", value);
	}

	@BeforeEach
	public void setUp() {
		this.roleConverter = new RoleConverter();
		this.roleConverter.setUserService(this.userService);
	}
}
