package es.nivel36.laie.ejb.user;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;

public class SimpleUserMapper implements Mapper<User, SimpleUserDto> {

	@Override
	public SimpleUserDto map(User entity) {
		SimpleUserDto dto = new SimpleUserDto();
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		final File picture = entity.getPicture();
		if (picture != null) {
			dto.setImage(picture.getPhysicalFile().getRelativePath());
		}
		dto.setUid(entity.getUid());
		return null;
	}

}
