package es.nivel36.laie.ejb.user;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;

public class SimpleUserMapper implements Mapper<User, SimpleUserDto> {

	@Override
	public SimpleUserDto map(final User entity) {
		if(entity == null) {
			return null;
		}
		final SimpleUserDto dto = new SimpleUserDto();
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		final File picture = entity.getPicture();
		if (picture != null) {
			dto.setAvatarUrl(picture.getPhysicalFile().getRelativePath());
		}
		dto.setUid(entity.getUid());
		return dto;
	}
}
