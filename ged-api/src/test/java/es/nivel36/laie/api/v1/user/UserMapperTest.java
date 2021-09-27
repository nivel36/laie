package es.nivel36.laie.api.v1.user;

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

import es.nivel36.laie.api.v1.user.UserDto;
import es.nivel36.laie.api.v1.user.UserMapper;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

	@Nested
	class MapDto {

		@Test
		public void mapDtoShouldReturnAnEntity() {
			final User boss = new User();
			boss.setEmail("boss@test.com");
			when(UserMapperTest.this.userService.findByEmail("boss@test.com")).thenReturn(boss);

			final UserDto userDto = UserMapperTest.this.mockUserDto();

			final User user = UserMapperTest.this.userMapper.mapDto(userDto);

			assertNotNull(user);
			assertEquals(userDto.getDateOfJoin(), user.getDateOfJoin());
			assertEquals(userDto.getEmail(), user.getEmail());
			assertEquals(userDto.getLanguage(), user.getLanguage());
			assertEquals(userDto.getLastConnection(), user.getLastConnection());
			assertEquals(userDto.getManagerEmail(), user.getManager().getEmail());
			assertEquals(userDto.getName(), user.getName());
			assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
			assertEquals(userDto.getRoleName(), user.getRole().name());
			assertEquals(userDto.getSurname(), user.getSurname());
		}

		@Test
		public void nullDtoShouldReturnNullEntity() {
			final User user = UserMapperTest.this.userMapper.mapDto(null);
			assertNull(user);
		}
	}

	@Nested
	class MapEntity {

		@Test
		public void mapEntityShouldReturnDto() {
			final User user = UserMapperTest.this.mockUser();

			final UserDto userDto = UserMapperTest.this.userMapper.mapEntity(user);

			assertNotNull(userDto);
			assertEquals(user.getDateOfJoin(), userDto.getDateOfJoin());
			assertEquals(user.getEmail(), userDto.getEmail());
			assertEquals(user.getLanguage(), userDto.getLanguage());
			assertEquals(user.getLastConnection(), userDto.getLastConnection());
			assertEquals(user.getManager().getEmail(), userDto.getManagerEmail());
			assertEquals(user.getName(), userDto.getName());
			assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
			assertEquals(user.getRole().name(), userDto.getRoleName());
			assertEquals(user.getSurname(), userDto.getSurname());
		}

		@Test
		public void nullEntityShouldReturnNullDto() {
			final UserDto userDto = UserMapperTest.this.userMapper.mapEntity(null);
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
		user.setLanguage("ES");
		user.setLastConnection(LocalDateTime.now());
		user.setName("Aaron");
		user.setPhoneNumber("123456789");
		user.setRole(Role.USER);
		user.setSurname("Smith");

		final User manager = new User();
		manager.setEmail("boss@test.com");
		user.setManager(manager);
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
		userDto.setRoleName(Role.USER.name());
		userDto.setSurname("Smith");
		return userDto;
	}

	@BeforeEach
	public void setUp() {
		this.userMapper = new UserMapper();
		this.userMapper.setUserService(this.userService);
	}
}
