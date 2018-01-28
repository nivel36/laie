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

	@Before
	public void setUp() {
		this.userMapper = new UserMapper(this.roleService, this.userService);
	}
}
