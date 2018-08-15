package ged.api.v1.user;

import javax.inject.Inject;

import ged.api.v1.mapper.AbstractMapper;
import ged.api.v1.mapper.Mapper;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;

@Mapper
public class UserMapper implements AbstractMapper<User, UserDto> {

	private final RoleService roleService;

	private final UserService userService;

	@Inject
	public UserMapper(final RoleService roleService, final UserService userService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	
	public User mapDto(final UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		final User user = new User();
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setEmail(userDto.getEmail());
		user.setImageFileName(userDto.getImageFileName());
		user.setLanguage(userDto.getLanguage());
		user.setLastConnection(userDto.getLastConnection());
		if (userDto.getManagerEmail() != null) {
			final User manager = this.userService.findUserByEmail(userDto.getManagerEmail());
			user.setManager(manager);
		}
		user.setName(userDto.getName());
		user.setPhoneNumber(userDto.getPhoneNumber());
		if (userDto.getRoleName() != null) {
			final Role role = this.roleService.findRoleByName(userDto.getRoleName());
			user.setRole(role);
		}
		user.setSurname(userDto.getSurname());
		return user;
	}

	
	public UserDto mapEntity(final User user) {
		if (user == null) {
			return null;
		}
		final UserDto userDto = new UserDto();
		userDto.setDateOfJoin(user.getDateOfJoin());
		userDto.setEmail(user.getEmail());
		userDto.setImageFileName(user.getImageFileName());
		userDto.setLanguage(user.getLanguage());
		userDto.setLastConnection(user.getLastConnection());
		if (user.getManager() != null) {
			userDto.setManagerEmail(user.getManager().getEmail());
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().getName());
		userDto.setSurname(user.getSurname());
		return userDto;
	}
}