package es.nivel36.laie.ejb.candidate;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;

public class CandidateMapper implements Mapper<Candidate, CandidateDto> {

	private AddressMapper addressMapper;

	public CandidateMapper() {
		addressMapper = new AddressMapper();
	}

	@Override
	public CandidateDto map(final Candidate entity) {
		if (entity == null) {
			return null;
		}
		final CandidateDto dto = new CandidateDto();
		final Address address = entity.getAddress();
		final AddressDto addressDto = addressMapper.map(address);
		dto.setAddress(addressDto);
		dto.setBornDate(entity.getBornDate());
		dto.setEmail(entity.getEmail());
		dto.setExpectedSalary(entity.getExpectedSalary());
		dto.setPicturesPath(entity.getPicture().getPhysicalFile().getRelativePath());
		dto.setInfojobsProfileUrl(entity.getInfojobsProfileUrl());
		dto.setJobProfile(entity.getJobProfile());
		dto.setLinkedinProfileUrl(entity.getLinkedinProfileUrl());
		dto.setName(entity.getName());
		dto.setOrigin(entity.getOrigin());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setRating(entity.getRating());
		dto.setSalary(entity.getSalary());
		dto.setSkype(entity.getSkype());
		dto.setSurname(entity.getSurname());
		return dto;
	}
}