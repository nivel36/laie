package es.nivel36.laie.ejb.core.model;

public class AddressMerger implements Merger<Address, AddressDto> {

	public void merge(Address entity, AddressDto dto) {
		if (entity == null && dto != null) {
			entity = new Address();
		}
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