package es.nivel36.laie.ejb.job.offer;

import java.util.Set;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;

public class JobOfferMapper implements Mapper<JobOffer, JobOfferDto> {
	
	private AddressMapper addressMapper;
	
	private SimpleUserMapper simpleUserMapper;
	
	public JobOfferMapper() {
		this.addressMapper = new AddressMapper();
		this.simpleUserMapper = new SimpleUserMapper();
	}

	@Override
	public JobOfferDto map(final JobOffer entity){
		final JobOfferDto dto = new JobOfferDto();
		AddressDto addressDto = addressMapper.map(entity.getAddress());
		dto.setAddress(addressDto);
		dto.setDateClosed(entity.getDateClosed());
		dto.setDateOpened(entity.getDateOpened());
		dto.setDescription(entity.getDescription());
		dto.setJobCandidatures(null);
		final SimpleUserDto owner = simpleUserMapper.map(entity.getOwner());
		dto.setOwner(owner);
		dto.setPlaces(entity.getPlaces());
		dto.setPublished(entity.isPublished());
		dto.setSalary(entity.getSalary());
		final Set<SimpleUserDto> recruiters = simpleUserMapper.mapSet(entity.getRecruiters());
		dto.setRecruiters(recruiters);
		dto.setSalary(entity.getSalary());
		dto.setState(entity.getState());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		return dto;
	}
}
