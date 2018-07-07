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

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.view.user.UserConverter;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private UserConverter userConverter;

	@Mock
	private UserService userService;

	@Test
	public void getAsObjectInvalidTest() {
		this.thrown.expect(ConverterException.class);
		this.userConverter.getAsObject(null, null, "qwq");
	}

	@Test
	public void getAsObjectNotFoundTest() {
		final User userFromDatabase = new User();
		userFromDatabase.setId(1L);
		userFromDatabase.setEmail("abel@test.com");
		Mockito.when(this.userService.find(1L)).thenReturn(userFromDatabase);
		final User user = this.userConverter.getAsObject(null, null, "2");
		Assert.assertNull(user);
	}

	@Test
	public void getAsObjectNullTest() {
		final User user = this.userConverter.getAsObject(null, null, null);
		Assert.assertNull(user);
	}

	@Test
	public void getAsObjectTest() {
		final User userFromDatabase = new User();
		userFromDatabase.setId(1L);
		userFromDatabase.setEmail("abel@test.com");
		Mockito.when(this.userService.find(1L)).thenReturn(userFromDatabase);
		final User user = this.userConverter.getAsObject(null, null, "1");
		Assert.assertEquals(userFromDatabase, user);
	}

	@Test
	public void getAsStringNullTest() {
		final String value = this.userConverter.getAsString(null, null, null);
		Assert.assertNull(value);
	}

	@Test
	public void getAsStringValidTest() {
		final User user = new User();
		user.setId(1L);
		final String value = this.userConverter.getAsString(null, null, user);
		Assert.assertEquals("1", value);
	}

	@Before
	public void setUp() {
		this.userConverter = new UserConverter();
		this.userConverter.setUserService(this.userService);
	}
}
