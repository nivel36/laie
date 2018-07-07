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

import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.view.user.RoleConverter;

@RunWith(MockitoJUnitRunner.class)
public class RoleConverterTest {

	private RoleConverter roleConverter;

	@Mock
	private RoleService roleService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

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
		Mockito.when(this.roleService.find(1L)).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "2");
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
		Mockito.when(this.roleService.find(1L)).thenReturn(roleFromDatabase);
		final Role role = this.roleConverter.getAsObject(null, null, "1");
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
		role.setId(1L);
		final String value = this.roleConverter.getAsString(null, null, role);
		Assert.assertEquals("1", value);
	}

	@Before
	public void setUp() {
		this.roleConverter = new RoleConverter();
		this.roleConverter.setRoleService(this.roleService);
	}

}
