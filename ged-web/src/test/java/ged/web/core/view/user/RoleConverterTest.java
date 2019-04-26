package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.role.Role;
import ged.web.view.user.RoleConverter;

@ExtendWith(MockitoExtension.class)
public class RoleConverterTest {

	@Nested
	class GetAsObject {

		@Test
		public void foundRoleShouldReturnRole() {
			final Role role = RoleConverterTest.this.roleConverter.getAsObject(null, null, "ADMIN");
			assertEquals(Role.ADMIN, role);
		}

		@Test
		public void nullStringShouldReturnNull() {
			final Role role = RoleConverterTest.this.roleConverter.getAsObject(null, null, null);
			assertNull(role);
		}

		@Test
		public void roleNotFoundShouldReturnNull() {
			Assertions.assertThrows(IllegalArgumentException.class,
					() -> RoleConverterTest.this.roleConverter.getAsObject(null, null, "USE"));
		}
	}

	@Nested
	class GetAsString {

		@Test
		public void nullRoleShouldReturnNull() {
			final String value = RoleConverterTest.this.roleConverter.getAsString(null, null, null);
			assertNull(value);
		}

		@Test
		public void validRoleShouldReturnRoleName() {
			final String value = RoleConverterTest.this.roleConverter.getAsString(null, null, Role.ADMIN);
			assertEquals("ADMIN", value);
		}
	}

	private RoleConverter roleConverter;

	@BeforeEach
	public void setUp() {
		this.roleConverter = new RoleConverter();

	}
}
