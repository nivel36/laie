package ged.api.v1.user;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

	@Mock
	private RoleService roleService;

	private UserMapper userMapper;

	@Mock
	private UserService userService;

	@Test
	public void mapDtoTest() {
		final User boss = new User();
		boss.setEmail("boss@test.com");
		Mockito.when(this.userService.findUserByEmail("boss@test.com")).thenReturn(boss);

		final Role adminRole = new Role();
		adminRole.setName("ADMIN");
		Mockito.when(this.roleService.findRoleByName("ADMIN")).thenReturn(adminRole);
		final UserDto userDto = this.mockUserDto();

		final User user = this.userMapper.mapDto(userDto);

		Assert.assertNotNull(user);
		Assert.assertEquals(userDto.getDateOfJoin(), user.getDateOfJoin());
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Assert.assertEquals(userDto.getImageFileName(), user.getImageFileName());
		Assert.assertEquals(userDto.getLanguage(), user.getLanguage());
		Assert.assertEquals(userDto.getLastConnection(), user.getLastConnection());
		Assert.assertEquals(userDto.getManagerEmail(), user.getManager().getEmail());
		Assert.assertEquals(userDto.getName(), user.getName());
		Assert.assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
		Assert.assertEquals(userDto.getRoleName(), user.getRole().getName());
		Assert.assertEquals(userDto.getSurname(), user.getSurname());
	}

	public void mapEntityTest() {
		final User user = this.mockUser();

		final UserDto userDto = this.userMapper.mapEntity(user);

		Assert.assertNotNull(userDto);
		Assert.assertEquals(user.getDateOfJoin(), userDto.getDateOfJoin());
		Assert.assertEquals(user.getEmail(), userDto.getEmail());
		Assert.assertEquals(user.getImageFileName(), userDto.getImageFileName());
		Assert.assertEquals(user.getLanguage(), userDto.getLanguage());
		Assert.assertEquals(user.getLastConnection(), userDto.getLastConnection());
		Assert.assertEquals(user.getManager().getEmail(), userDto.getManagerEmail());
		Assert.assertEquals(user.getName(), userDto.getName());
		Assert.assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
		Assert.assertEquals(user.getRole().getName(), userDto.getRoleName());
		Assert.assertEquals(user.getSurname(), userDto.getSurname());
	}

	@Test
	public void mapNullDtoTest() {
		final User user = this.userMapper.mapDto(null);
		Assert.assertNull(user);
	}

	@Test
	public void mapNullEntityTest() {
		final UserDto userDto = this.userMapper.mapEntity(null);
		Assert.assertNull(userDto);
	}

	private User mockUser() {
		final User user = new User();
		user.setDateOfJoin(new Date());
		user.setEmail("aaron@test.com");
		user.setImageFileName("1");
		user.setLanguage("ES");
		user.setLastConnection(new Date());
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
		userDto.setDateOfJoin(new Date());
		userDto.setEmail("aaron@test.com");
		userDto.setImageFileName("1");
		userDto.setLanguage("ES");
		userDto.setLastConnection(new Date());
		userDto.setManagerEmail("boss@test.com");
		userDto.setName("Aaron");
		userDto.setPhoneNumber("123456789");
		userDto.setRoleName("ADMIN");
		userDto.setSurname("Smith");
		return userDto;
	}

	@Before
	public void setUp() {
		this.userMapper = new UserMapper(this.roleService, this.userService);
	}
}
