package es.nivel36.laie.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.view.user.UserConverter;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

	@Nested
	class GetAsObject {

		@Test
		public void foundRoleShouldReturnRole() {
			final User userFromDatabase = mockUser();

			when(userService.findUserByUid("1")).thenReturn(userFromDatabase);

			final User user = userConverter.getAsObject(null, null, "1");
			assertEquals(userFromDatabase, user);
		}

		@Test
		public void nullStringShouldReturnNull() {
			final User user = userConverter.getAsObject(null, null, null);
			assertNull(user);
		}

		@Test
		public void roleNotFoundShouldReturnNull() {
			when(userService.findUserByUid("1")).thenReturn(null);

			final User user = userConverter.getAsObject(null, null, "1");
			assertNull(user);
		}
	}

	@Nested
	class GetAsString {

		@Test
		public void nullRoleShouldReturnNull() {
			final String value = userConverter.getAsString(null, null, null);
			assertNull(value);
		}

		@Test
		public void validRoleShouldReturnRoleName() {
			final User user = mockUser();

			final String value = userConverter.getAsString(null, null, user);
			assertEquals("1", value);
		}
	}

	private UserConverter userConverter;

	@Mock
	private UserService userService;

	private User mockUser() {
		final User user = new User();
		user.setId(1L);
		user.setUid("1");
		user.setEmail("abel@test.com");
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.userConverter = new UserConverter();
		this.userConverter.setUserService(this.userService);
	}
}
