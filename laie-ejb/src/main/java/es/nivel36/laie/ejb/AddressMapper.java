package es.nivel36.laie.ejb;

import es.nivel36.core.model.Mapper;

public class AddressMapper implements Mapper<Address, AddressDto> {

	@Override
	public AddressDto map(final Address entity) {
		if (entity == null) {
			return null;
		}
		AddressDto dto = new AddressDto();
		dto.setCity(entity.getCity());
		dto.setCountry(entity.getCountry());
		dto.setDoor(entity.getDoor());
		dto.setNumber(entity.getNumber());
		dto.setRegion(entity.getRegion());
		dto.setStorey(entity.getStorey());
		dto.setStreet(entity.getStreet());
		dto.setZipCode(entity.getZipCode());
		return dto;
	}
}
