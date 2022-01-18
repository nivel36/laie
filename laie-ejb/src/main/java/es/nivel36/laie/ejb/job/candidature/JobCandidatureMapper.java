package es.nivel36.laie.ejb.job.candidature;

import java.util.Set;

import es.nivel36.laie.ejb.candidate.SimpleCandidateDto;
import es.nivel36.laie.ejb.candidate.SimpleCandidateMapper;
import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingMapper;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferMapper;

public class JobCandidatureMapper implements Mapper<JobCandidature, JobCandidatureDto> {

	private SimpleCandidateMapper simpleCandidateMapper;
	
	private MeetingMapper meetingMapper;
	
	private JobOfferMapper jobOfferMapper;

	public JobCandidatureMapper() {
		simpleCandidateMapper = new SimpleCandidateMapper();
		jobOfferMapper = new JobOfferMapper();
		meetingMapper = new MeetingMapper();
	}

	@Override
	public JobCandidatureDto map(final JobCandidature entity) {
		if(entity == null) {
			return null;
		}
		final JobCandidatureDto dto = new JobCandidatureDto();
		final SimpleCandidateDto candidate = simpleCandidateMapper.map(entity.getCandidate());
		dto.setCandidate(candidate);
		final JobOfferDto jobOfferDto = jobOfferMapper.map(entity.getJobOffer());
		dto.setJobOffer(jobOfferDto);
		final Set<MeetingDto> meetingDtos = meetingMapper.mapSet(entity.getJobMeetings());
		dto.setJobMeetings(meetingDtos);
		dto.setState(entity.getState());
		return dto;
	}
}
