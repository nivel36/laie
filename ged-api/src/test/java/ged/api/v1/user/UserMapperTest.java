package ged.api.v1.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

	@Nested
	class MapDto {

		@Test
		public void mapDtoShouldReturnAnEntity() {
			final User boss = new User();
			boss.setEmail("boss@test.com");
			when(userService.findUserByEmail("boss@test.com")).thenReturn(boss);

			final Role adminRole = new Role();
			adminRole.setName("ADMIN");
			when(userService.findRoleByName("ADMIN")).thenReturn(adminRole);

			final UserDto userDto = mockUserDto();

			final User user = userMapper.mapDto(userDto);

			assertNotNull(user);
			assertEquals(userDto.getDateOfJoin(), user.getDateOfJoin());
			assertEquals(userDto.getEmail(), user.getEmail());
			assertEquals(userDto.getImageFileName(), user.getImageFileName());
			assertEquals(userDto.getLanguage(), user.getLanguage());
			assertEquals(userDto.getLastConnection(), user.getLastConnection());
			assertEquals(userDto.getManagerEmail(), user.getManager().getEmail());
			assertEquals(userDto.getName(), user.getName());
			assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
			assertEquals(userDto.getRoleName(), user.getRole().getName());
			assertEquals(userDto.getSurname(), user.getSurname());
		}

		@Test
		public void nullDtoShouldReturnNullEntity() {
			final User user = userMapper.mapDto(null);
			assertNull(user);
		}
	}

	@Nested
	class MapEntity {

		@Test
		public void mapEntityShouldReturnDto() {
			final User user = mockUser();

			final UserDto userDto = userMapper.mapEntity(user);

			assertNotNull(userDto);
			assertEquals(user.getDateOfJoin(), userDto.getDateOfJoin());
			assertEquals(user.getEmail(), userDto.getEmail());
			assertEquals(user.getImageFileName(), userDto.getImageFileName());
			assertEquals(user.getLanguage(), userDto.getLanguage());
			assertEquals(user.getLastConnection(), userDto.getLastConnection());
			assertEquals(user.getManager().getEmail(), userDto.getManagerEmail());
			assertEquals(user.getName(), userDto.getName());
			assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
			assertEquals(user.getRole().getName(), userDto.getRoleName());
			assertEquals(user.getSurname(), userDto.getSurname());
		}

		@Test
		public void nullEntityShouldReturnNullDto() {
			final UserDto userDto = userMapper.mapEntity(null);
			assertNull(userDto);
		}
	}

	private UserMapper userMapper;

	@Mock
	private UserService userService;

	private User mockUser() {
		final User user = new User();
		user.setDateOfJoin(LocalDate.now());
		user.setEmail("aaron@test.com");
		user.setImageFileName("1");
		user.setLanguage("ES");
		user.setLastConnection(LocalDateTime.now());
		final User manager = new User();
		manager.setEmail("boss@test.com");
		user.setManager(manager);
		user.setName("Aaron");
		user.setPhoneNumber("123456789");
		final Role role = new Role();
		role.setName("ADMIN");
		user.setRole(role);
		user.setSurname("Smith");
		return user;
	}

	private UserDto mockUserDto() {
		final UserDto userDto = new UserDto();
		userDto.setDateOfJoin(LocalDate.now());
		userDto.setEmail("aaron@test.com");
		userDto.setImageFileName("1");
		userDto.setLanguage("ES");
		userDto.setLastConnection(LocalDateTime.now());
		userDto.setManagerEmail("boss@test.com");
		userDto.setName("Aaron");
		userDto.setPhoneNumber("123456789");
		userDto.setRoleName("ADMIN");
		userDto.setSurname("Smith");
		return userDto;
	}

	@BeforeEach
	public void setUp() {
		userMapper = new UserMapper();
		userMapper.setUserService(userService);
	}
}
