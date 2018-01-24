package ged.api.v1.user;

import ged.api.v1.mapper.AbstractMapper;
import ged.api.v1.mapper.Mapper;
import ged.ejb.user.User;

@Mapper
public class UserMapper extends AbstractMapper<User, UserDto> {

	@Override
	public User mapDto(final UserDto userDto) {
		final User user = new User();
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setEmail(userDto.getEmail());
		user.setImageFileName(userDto.getImageFileName());
		user.setLanguage(userDto.getLanguage());
		// if (user.getManager() != null) {
		// //final User manager =
		// this.userService.findUserByEmail(userDto.getManagerEmail());
		// user.setManager(manager);
		// }
		// user.setName(userDto.getName());
		// user.setPhoneNumber(userDto.getPhoneNumber());
		// final List<Role> roles = this.roleService.findAllRoles();
		// for (final Role role : roles) {
		// if (role.getName().equals(userDto.getRoleName())) {
		// user.setRole(role);
		// }
		// }
		// user.setSurename(userDto.getSurename());
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
		userDto.setImageFileName(user.getImageFileName());
		userDto.setLanguage(user.getLanguage());
		userDto.setLastConnection(user.getLastConnection());
		if (user.getManager() != null) {
			userDto.setManagerEmail(user.getManager().getEmail());
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().getName());
		userDto.setSurename(user.getSurename());
		return userDto;
	}
}