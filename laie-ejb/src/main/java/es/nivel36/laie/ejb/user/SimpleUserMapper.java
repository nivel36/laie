package es.nivel36.laie.ejb.user;

import java.util.Objects;

import es.nivel36.core.model.Mapper;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;

public class SimpleUserMapper implements Mapper<User, SimpleUserDto> {

	private FileService fileService;

	public SimpleUserMapper(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	@Override
	public SimpleUserDto map(final User entity) {
		if (entity == null) {
			return null;
		}
		final SimpleUserDto dto = new SimpleUserDto();
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		final String picture = entity.getPictureUid();
		if (picture != null) {
			final FileDto fileDto = fileService.findByUid(picture);
			dto.setAvatarUrl(fileDto.getPath());
		}
		dto.setUid(entity.getUid());
		return dto;
	}
}
