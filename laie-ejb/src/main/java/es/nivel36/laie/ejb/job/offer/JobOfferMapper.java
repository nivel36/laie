package es.nivel36.laie.ejb.job.offer;

import java.util.Set;

import es.nivel36.laie.ejb.client.SimpleClientDto;
import es.nivel36.laie.ejb.client.SimpleClientMapper;
import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureMapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;

public class JobOfferMapper implements Mapper<JobOffer, JobOfferDto> {
	
	private AddressMapper addressMapper;
	
	private SimpleUserMapper simpleUserMapper;
	
	private SimpleClientMapper simpleClientMapper;
	
	private JobCandidatureMapper jobCandidatureMapper;
	
	public JobOfferMapper() {
		this.addressMapper = new AddressMapper();
		this.simpleUserMapper = new SimpleUserMapper();
		this.simpleClientMapper = new SimpleClientMapper();
		this.jobCandidatureMapper = new JobCandidatureMapper();
	}

	@Override
	public JobOfferDto map(final JobOffer entity){
		final JobOfferDto dto = new JobOfferDto();
		final AddressDto addressDto = this.addressMapper.map(entity.getAddress());
		dto.setAddress(addressDto);
		final SimpleClientDto clientDto = this.simpleClientMapper.map(entity.getClient());
		dto.setClient(clientDto);
		dto.setCloseDate(entity.getCloseDate());
		dto.setOpenDate(entity.getOpenDate());
		dto.setDescription(entity.getDescription());
		final SimpleUserDto owner = this.simpleUserMapper.map(entity.getOwner());
		dto.setOwner(owner);
		dto.setPlaces(entity.getPlaces());
		dto.setPublished(entity.isPublished());
		dto.setSalary(entity.getSalary());
		final Set<SimpleUserDto> recruiters = this.simpleUserMapper.mapSet(entity.getRecruiters());
		dto.setRecruiters(recruiters);
		dto.setSalary(entity.getSalary());
		dto.setState(entity.getState());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		final Set<JobCandidatureDto> jobCandidatures = this.jobCandidatureMapper.mapSet(entity.getJobCandidatures());
		dto.setJobCandidatures(jobCandidatures);
		return dto;
	}
}
