package es.nivel36.laie.ejb;

import java.util.Objects;

import es.nivel36.core.model.Merger;

public class AddressMerger implements Merger<Address, AddressDto> {

	public void merge(final Address entity, final AddressDto dto) {
		Objects.requireNonNull(dto);
		Objects.requireNonNull(entity);
		entity.setCity(dto.getCity());
		entity.setCountry(dto.getCountry());
		entity.setDoor(dto.getDoor());
		entity.setNumber(dto.getNumber());
		entity.setRegion(dto.getRegion());
		entity.setStorey(dto.getStorey());
		entity.setStreet(dto.getStreet());
		entity.setZipCode(dto.getZipCode());
	}
}