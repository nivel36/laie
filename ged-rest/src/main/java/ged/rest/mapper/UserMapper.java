package ged.rest.mapper;

import ged.ejb.user.User;
import ged.rest.user.UserDto;

public class UserMapper {

	public UserMapper() {

	}

	public User toObject(final UserDto userDto) {
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
}