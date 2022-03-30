package es.nivel36.files;

import es.nivel36.core.model.Mapper;

public class FileMapper implements Mapper<File, FileDto> {

	@Override
	public FileDto map(File entity) {
		FileDto dto = new FileDto();
		dto.setCreated(entity.getCreated());
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		dto.setPath(entity.getPhysicalFile().getRelativePath());
		dto.setPublicAccess(entity.isPublicAccess());
		dto.setUid(entity.getUid());
		return dto;
	}

}
