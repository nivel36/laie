package es.nivel36.laie.ejb.event;

import es.nivel36.core.model.Mapper;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;

public class JobCandidatureEventMapper implements Mapper<JobCandidatureEvent, JobCandidatureEventDto> {

	private SimpleUserMapper simpleUserMapper;

	public JobCandidatureEventMapper(final FileService fileService) {
		simpleUserMapper = new SimpleUserMapper(fileService);
	}

	@Override
	public JobCandidatureEventDto map(final JobCandidatureEvent entity) {
		if (entity == null) {
			return null;
		}
		final JobCandidatureEventDto dto = new JobCandidatureEventDto();
		dto.setDate(entity.getDate());
		dto.setNotes(entity.getNotes());
		dto.setState(entity.getState());
		dto.setType(entity.getType());
		final SimpleUserDto user = simpleUserMapper.map(entity.getUser());
		dto.setUser(user);
		return dto;
	}
}
