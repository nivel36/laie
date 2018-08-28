package ged.web.core.view.user;

import javax.faces.convert.ConverterException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.web.view.user.RoleConverter;

@RunWith(MockitoJUnitRunner.class)
public class RoleConverterTest {

	private RoleConverter roleConverter;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private UserService userService;

	@Test
	public void getAsObjectInvalidTest() {
		this.thrown.expect(ConverterException.class);
		this.roleConverter.getAsObject(null, null, "qwq");
	}

	@Test
	public void getAsObjectNotFoundTest() {
		final Role roleFromDatabase = new Role();
		roleFromDatabase.setId(1L);
		roleFromDatabase.setName("ADMIN");
		Mockito.when(this.userService.findRoleByName("ADMIN")).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "USER");
		Assert.assertNull(role);
	}

	@Test
	public void getAsObjectNullTest() {
		final Role role = this.roleConverter.getAsObject(null, null, null);
		Assert.assertNull(role);
	}

	@Test
	public void getAsObjectTest() {
		final Role roleFromDatabase = new Role();
		roleFromDatabase.setId(1L);
		roleFromDatabase.setName("ADMIN");
		Mockito.when(this.userService.findRoleByName("ADMIN")).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "ADMIN");
		Assert.assertEquals(roleFromDatabase, role);
	}

	@Test
	public void getAsStringNullTest() {
		final String value = this.roleConverter.getAsString(null, null, null);
		Assert.assertNull(value);
	}

	@Test
	public void getAsStringValidTest() {
		final Role role = new Role();
		role.setName("ADMIN");
		final String value = this.roleConverter.getAsString(null, null, role);
		Assert.assertEquals("ADMIN", value);
	}

	@Before
	public void setUp() {
		this.roleConverter = new RoleConverter();
		this.roleConverter.setUserService(this.userService);
	}

}
