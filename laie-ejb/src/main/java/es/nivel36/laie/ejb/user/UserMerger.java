package es.nivel36.laie.ejb.user;

import es.nivel36.laie.ejb.core.model.Merger;

public class UserMerger implements Merger<User, UserDto> {

	public UserMerger() {
	}

	@Override
	public void merge(User user, UserDto userDto) {
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setEmail(userDto.getEmail());
		user.setLanguage(userDto.getLanguage());
		user.setLastConnection(userDto.getLastConnection());
		user.setName(userDto.getName());
		user.setPhoneNumber(userDto.getPhoneNumber());
		final String roleName = userDto.getRoleName();
		if (roleName != null) {
			user.setRole(Role.valueOf(roleName));
		}
		user.setSurname(userDto.getSurname());
	}
}
