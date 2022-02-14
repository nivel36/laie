package es.nivel36.laie.ejb.job.candidature;

import java.util.Set;

import es.nivel36.laie.ejb.candidate.SimpleCandidateDto;
import es.nivel36.laie.ejb.candidate.SimpleCandidateMapper;
import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingMapper;
import es.nivel36.laie.ejb.job.offer.SimpleJobOfferDto;
import es.nivel36.laie.ejb.job.offer.SimpleJobOfferMapper;

public class JobCandidatureMapper implements Mapper<JobCandidature, JobCandidatureDto> {

	private SimpleCandidateMapper simpleCandidateMapper;
	
	private SimpleJobOfferMapper simpleJobOfferMapper;
	
	private MeetingMapper meetingMapper;
	
	public JobCandidatureMapper() {
		this.simpleCandidateMapper = new SimpleCandidateMapper();
		this.meetingMapper = new MeetingMapper();
		this.simpleJobOfferMapper = new SimpleJobOfferMapper();
	}

	@Override
	public JobCandidatureDto map(final JobCandidature entity) {
		if(entity == null) {
			return null;
		}
		final JobCandidatureDto dto = new JobCandidatureDto();
		final SimpleCandidateDto candidate = simpleCandidateMapper.map(entity.getCandidate());
		dto.setCandidate(candidate);
		final Set<MeetingDto> meetingDtos = meetingMapper.mapSet(entity.getJobMeetings());
		dto.setJobMeetings(meetingDtos);
		dto.setState(entity.getState());
		final SimpleJobOfferDto simpleJobOfferDto = simpleJobOfferMapper.map(entity.getJobOffer());
		dto.setJobOffer(simpleJobOfferDto);
		return dto;
	}
}