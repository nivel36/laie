package es.nivel36.laie.ejb.curriculum.jobexperience;

import es.nivel36.laie.ejb.core.Mapper;

public class JobExperienceMapper implements Mapper<JobExperience, JobExperienceDto> {

	@Override
	public JobExperienceDto map(final JobExperience entity) {
		if (entity == null) {
			return null;
		}
		final JobExperienceDto dto = new JobExperienceDto();
		dto.setCompanyName(entity.getCompanyName());
		dto.setDescription(entity.getDescription());
		dto.setEndDate(entity.getEndDate());
		dto.setJobPosition(entity.getJobPosition());
		dto.setStartDate(entity.getStartDate());
		dto.setStillWorking(entity.isStillWorking());
		return dto;
	}
}	
