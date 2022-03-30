package es.nivel36.laie.ejb.candidate;

import java.util.Objects;

import es.nivel36.core.model.Mapper;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;

public class SimpleCandidateMapper implements Mapper<Candidate, SimpleCandidateDto> {

	private FileService fileService;

	public SimpleCandidateMapper(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	@Override
	public SimpleCandidateDto map(final Candidate entity) {
		final SimpleCandidateDto dto = new SimpleCandidateDto();
		dto.setUid(entity.getUid());
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		final FileDto fileDto = fileService.findByUid(entity.getPictureUid());
		if (fileDto != null) {
			dto.setAvatarUrl(fileDto.getPath());
		}
		dto.setRating(entity.getRating());
		return dto;
	}
}
