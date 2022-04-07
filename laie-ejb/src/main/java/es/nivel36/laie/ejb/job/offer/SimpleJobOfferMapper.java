package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.client.SimpleClientDto;
import es.nivel36.laie.ejb.client.SimpleClientMapper;
import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;

public class SimpleJobOfferMapper implements Mapper<JobOffer, SimpleJobOfferDto> {

	private SimpleClientMapper simpleClientMapper;
	
	private AddressMapper addressMapper;

	public SimpleJobOfferMapper() {
		this.simpleClientMapper = new SimpleClientMapper();
		this.addressMapper = new AddressMapper();
	}

	@Override
	public SimpleJobOfferDto map(final JobOffer entity) {
		final SimpleJobOfferDto dto = new SimpleJobOfferDto();
		dto.setState(entity.getState());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		final SimpleClientDto clientDto = this.simpleClientMapper.map(entity.getClient());
		dto.setClient(clientDto);
		final AddressDto addressDto = this.addressMapper.map(entity.getAddress());
		dto.setAddress(addressDto);
		return dto;
	}
}
