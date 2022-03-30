package es.nivel36.laie.ejb.job.meeting;

import es.nivel36.core.model.Mapper;
import es.nivel36.laie.ejb.job.offer.SimpleJobOfferDto;
import es.nivel36.laie.ejb.job.offer.SimpleJobOfferMapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;

public class MeetingMapper implements Mapper<Meeting, MeetingDto> {

	private SimpleJobOfferMapper simpleJobOfferMapper;
	
	private SimpleUserMapper simpleUserMapper;

	public MeetingMapper() {
		simpleJobOfferMapper = new SimpleJobOfferMapper();
	}

	@Override
	public MeetingDto map(final Meeting entity) {
		final MeetingDto dto = new MeetingDto();
		dto.setAttendeesEmails(entity.getAttendeesEmails());
		dto.setDatePlanned(entity.getDatePlanned());
		dto.setDuration(entity.getDuration());
		final SimpleJobOfferDto jobOffer = simpleJobOfferMapper.map(entity.getJobOffer());
		dto.setJobOffer(jobOffer);
		dto.setLocation(entity.getLocation());
		dto.setMeetingType(entity.getMeetingType());
		final SimpleUserDto owner = simpleUserMapper.map(entity.getOwner());
		dto.setOwner(owner);
		dto.setResult(entity.getResult());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		return dto;
	}
}