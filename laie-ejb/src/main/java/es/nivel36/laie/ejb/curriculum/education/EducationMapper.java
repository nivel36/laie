package es.nivel36.laie.ejb.curriculum.education;

import es.nivel36.core.model.Mapper;

public class EducationMapper implements Mapper<Education, EducationDto> {

	@Override
	public EducationDto map(final Education entity) {
		if (entity == null) {
			return null;
		}
		final EducationDto dto = new EducationDto();
		dto.setDegree(entity.getDegree());
		dto.setDescription(entity.getDescription());
		dto.setEndYear(entity.getEndYear());
		dto.setSchool(entity.getSchool());
		dto.setStartYear(entity.getStartYear());
		dto.setStillStudying(entity.isStillStudying());
		return dto;
	}
}