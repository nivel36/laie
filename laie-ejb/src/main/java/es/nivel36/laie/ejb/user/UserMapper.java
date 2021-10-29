package es.nivel36.laie.ejb.user;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;

public class UserMapper implements Mapper<User, UserDto> {

	@Override
	public UserDto map(final User user) {
		if (user == null) {
			return null;
		}
		final UserDto userDto = new UserDto();
		userDto.setDateOfJoin(user.getDateOfJoin());
		userDto.setEmail(user.getEmail());
		userDto.setLanguage(user.getLanguage());
		userDto.setLastConnection(user.getLastConnection());
		final User manager = user.getManager();
		if (manager != null) {
			final SimpleUserDto managerDto = new SimpleUserDto();
			managerDto.setEmail(manager.getEmail());
			managerDto.setFullName(manager.getFullName());
			final File picture = manager.getPicture();
			if (picture != null) {
				managerDto.setImage(picture.getPhysicalFile().getRelativePath());
			}
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().name());
		userDto.setSurname(user.getSurname());
		userDto.setUid(user.getUid());
		return userDto;
	}
}