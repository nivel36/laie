package es.nivel36.laie.ejb.event;

import es.nivel36.core.model.Merger;

public class JobCandidatureEventMerger implements Merger<JobCandidatureEvent, JobCandidatureEventDto> {

	@Override
	public void merge(JobCandidatureEvent entity, JobCandidatureEventDto dto) {
		entity.setDate(dto.getDate());
		entity.setNotes(dto.getNotes());
		entity.setType(null);
	}
}
