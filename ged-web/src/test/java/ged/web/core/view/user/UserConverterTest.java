package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.view.user.UserConverter;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

	private UserConverter userConverter;

	@Mock
	private UserService userService;

	@Test
	public void getAsObjectInvalidTest() {
		this.userConverter.getAsObject(null, null, "qwq");
	}

	@Test
	public void getAsObjectNotFoundTest() {
		final User userFromDatabase = new User();
		userFromDatabase.setId(1L);
		userFromDatabase.setEmail("abel@test.com");
		Mockito.when(this.userService.find(1L)).thenReturn(userFromDatabase);
		final User user = this.userConverter.getAsObject(null, null, "2");
		assertNull(user);
	}

	@Test
	public void getAsObjectNullTest() {
		final User user = this.userConverter.getAsObject(null, null, null);
		assertNull(user);
	}

	@Test
	public void getAsObjectTest() {
		final User userFromDatabase = new User();
		userFromDatabase.setId(1L);
		userFromDatabase.setEmail("abel@test.com");
		Mockito.when(this.userService.find(1L)).thenReturn(userFromDatabase);
		final User user = this.userConverter.getAsObject(null, null, "1");
		assertEquals(userFromDatabase, user);
	}

	@Test
	public void getAsStringNullTest() {
		final String value = this.userConverter.getAsString(null, null, null);
		assertNull(value);
	}

	@Test
	public void getAsStringValidTest() {
		final User user = new User();
		user.setId(1L);
		final String value = this.userConverter.getAsString(null, null, user);
		assertEquals("1", value);
	}

	@BeforeEach
	public void setUp() {
		this.userConverter = new UserConverter();
		this.userConverter.setUserService(this.userService);
	}
}
