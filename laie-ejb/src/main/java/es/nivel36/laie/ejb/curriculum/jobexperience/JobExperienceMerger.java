package es.nivel36.laie.ejb.curriculum.jobexperience;

import java.util.Objects;

import es.nivel36.core.model.Merger;

public class JobExperienceMerger implements Merger<JobExperience, JobExperienceDto> {

	@Override
	public void merge(final JobExperience entity, final JobExperienceDto dto) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(dto);
		entity.setCompanyName(dto.getCompanyName());
		entity.setDescription(dto.getDescription());
		entity.setEndDate(dto.getEndDate());
		entity.setJobPosition(dto.getJobPosition());
		entity.setStartDate(dto.getStartDate());
		entity.setStillWorking(dto.isStillWorking());
	}
}
