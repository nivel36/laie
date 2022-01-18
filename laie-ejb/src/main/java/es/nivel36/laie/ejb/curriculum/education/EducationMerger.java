package es.nivel36.laie.ejb.curriculum.education;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.Merger;

public class EducationMerger implements Merger<Education, EducationDto>{

	@Override
	public void merge(Education entity, EducationDto dto) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(dto);
		entity.setDegree(dto.getDegree());
		entity.setDescription(dto.getDegree());
		entity.setEndYear(dto.getEndYear());
		entity.setSchool(dto.getSchool());
		entity.setStartYear(dto.getStartYear());
		entity.setStillStudying(dto.isStillStudying());	
	}
}
