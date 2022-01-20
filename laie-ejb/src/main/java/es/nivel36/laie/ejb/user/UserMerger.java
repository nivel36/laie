package es.nivel36.laie.ejb.user;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.Merger;

public class UserMerger implements Merger<User, UserDto> {

	@Override
	public void merge(final User user, final UserDto userDto) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(userDto);
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
