package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.core.model.Merger;

public class JobCandidatureStateMerger implements Merger<JobCandidatureState, JobCandidatureStateDto> {

	@Override
	public void merge(final JobCandidatureState entity, final JobCandidatureStateDto dto) {
		entity.setApproved(dto.isApproved());
		entity.setDeclined(dto.isDeclined());
		entity.setFirst(dto.isFirst());
		entity.setName(dto.getName());
	}
}
