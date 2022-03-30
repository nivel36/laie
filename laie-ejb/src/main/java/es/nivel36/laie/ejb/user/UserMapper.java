package es.nivel36.laie.ejb.user;

import java.util.Objects;

import es.nivel36.core.model.Mapper;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;

public class UserMapper implements Mapper<User, UserDto> {

	private FileService fileService;

	public UserMapper(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

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
			managerDto.setUid(manager.getUid());
			final String picture = manager.getPictureUid();
			if (picture != null) {
				final FileDto fileDto = this.fileService.findByUid(picture);
				managerDto.setAvatarUrl(fileDto.getPath());
			}
			userDto.setManager(managerDto);
		}
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRoleName(user.getRole().name());
		userDto.setSurname(user.getSurname());
		userDto.setUid(user.getUid());
		final String userPicture = user.getPictureUid();
		if (userPicture != null) {
			final FileDto fileDto = this.fileService.findByUid(userPicture);
			userDto.setAvatarUrl(fileDto.getPath());
		}
		return userDto;
	}
}