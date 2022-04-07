package es.nivel36.laie.ejb.event;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;

public class JobOfferEventMapper implements Mapper<JobOfferEvent, JobOfferEventDto> {

	private SimpleUserMapper simpleUserMapper;

	public JobOfferEventMapper() {
		simpleUserMapper = new SimpleUserMapper();
	}

	@Override
	public JobOfferEventDto map(final JobOfferEvent entity) {
		final JobOfferEventDto dto = new JobOfferEventDto();
		dto.setDate(entity.getDate());
		dto.setNotes(entity.getNotes());
		dto.setState(entity.getState());
		final SimpleUserDto user = simpleUserMapper.map(entity.getUser());
		dto.setUser(user);
		return dto;
	}
}
