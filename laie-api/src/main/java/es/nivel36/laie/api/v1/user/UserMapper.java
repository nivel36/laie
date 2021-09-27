package es.nivel36.laie.api.v1.user;

import javax.inject.Inject;

import es.nivel36.laie.api.v1.mapper.AbstractMapper;
import es.nivel36.laie.api.v1.mapper.Mapper;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@Mapper
public class UserMapper implements AbstractMapper<User, UserDto> {

	@Inject
	private UserService userService;

	@Override
	public User mapDto(final UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		final User user = new User();
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setEmail(userDto.getEmail());
		user.setLanguage(userDto.getLanguage());
		user.setLastConnection(userDto.getLastConnection());
		if (userDto.getManagerEmail() != null) {
			final User manager = this.userService.findByEmail(userDto.getManagerEmail());
			user.setManager(manager);
		}
		user.setName(userDto.getName());
		user.setPhoneNumber(userDto.getPhoneNumber());
		final String roleName = userDto.getRoleName();
		if (roleName != null) {
			user.setRole(Role.valueOf(roleName));
		}
		user.setSurname(userDto.getSurname());
		return user;
	}

	@Override
	public UserDto mapEntity(final User user) {
		if (user == null) {
			return null;
		}
		final UserDto userDto = new UserDto();
		userDto.setDateOfJoin(user.getDateOfJoin());
		userDto.setEmail(user.getEmail());
		userDto.setLanguage(user.getLanguage());
		userDto.setLastConnection(user.getLastConnection());
		if (user.getManager() != null) {
			userDto.setManagerEmail(user.getManager().getEmail());
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().name());
		userDto.setSurname(user.getSurname());
		return userDto;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}