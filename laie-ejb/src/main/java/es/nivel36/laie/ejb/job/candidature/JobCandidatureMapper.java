package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.laie.ejb.candidate.SimpleCandidateDto;
import es.nivel36.laie.ejb.candidate.SimpleCandidateMapper;
import es.nivel36.laie.ejb.core.Mapper;

public class JobCandidatureMapper implements Mapper<JobCandidature, JobCandidatureDto> {

	private SimpleCandidateMapper simpleCandidateMapper;

	public JobCandidatureMapper() {
		simpleCandidateMapper = new SimpleCandidateMapper();
	}

	@Override
	public JobCandidatureDto map(JobCandidature entity) {
		final JobCandidatureDto dto = new JobCandidatureDto();
		final SimpleCandidateDto candidate = simpleCandidateMapper.map(entity.getCandidate());
		dto.setCandidate(candidate);
		dto.setJobOffer(null);
		return dto;
	}
}
