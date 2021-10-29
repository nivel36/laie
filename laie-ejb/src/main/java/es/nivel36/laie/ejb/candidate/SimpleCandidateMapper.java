package es.nivel36.laie.ejb.candidate;

import es.nivel36.laie.ejb.core.Mapper;

public class SimpleCandidateMapper implements Mapper<Candidate, SimpleCandidateDto> {

	@Override
	public SimpleCandidateDto map(final Candidate entity) {
		final SimpleCandidateDto dto = new SimpleCandidateDto();
		dto.setCandidateUid(entity.getUid());
		dto.setCandidateUid(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setPhoto(entity.getPicture().getPhysicalFile().getRelativePath());
		return dto;
	}
}
