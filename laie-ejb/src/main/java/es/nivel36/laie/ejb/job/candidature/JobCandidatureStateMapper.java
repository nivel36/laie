package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.laie.ejb.core.Mapper;

public class JobCandidatureStateMapper implements Mapper<JobCandidatureState, JobCandidatureStateDto> {

	@Override
	public JobCandidatureStateDto map(final JobCandidatureState entity) {
		if (entity == null) {
			return null;
		}
		final JobCandidatureStateDto dto = new JobCandidatureStateDto();
		dto.setApproved(entity.isApproved());
		dto.setDeclined(entity.isDeclined());
		dto.setFirst(entity.isFirst());
		dto.setName(entity.getName());
		return dto;
	}
}
