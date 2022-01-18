package es.nivel36.laie.ejb.candidate;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.PhysicalFile;

public class SimpleCandidateMapper implements Mapper<Candidate, SimpleCandidateDto> {

	@Override
	public SimpleCandidateDto map(final Candidate entity) {
		final SimpleCandidateDto dto = new SimpleCandidateDto();
		dto.setUid(entity.getUid());
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		final File picture = entity.getPicture();
		if (picture != null) {
			final PhysicalFile file = picture.getPhysicalFile();
			dto.setAvatarUrl(file.getAbsolutePath());
		}
		dto.setRating(entity.getRating());
		return dto;
	}
}
