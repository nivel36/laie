package es.nivel36.laie.ejb.client;

import es.nivel36.laie.ejb.core.Mapper;

public class ContactMapper implements Mapper<Contact, ContactDto> {

	@Override
	public ContactDto map(final Contact entity) {
		final ContactDto dto = new ContactDto();
		dto.setEmail(entity.getEmail());
		dto.setLanguage(entity.getLanguage());
		dto.setName(entity.getName());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setPosition(entity.getPosition());
		dto.setSurname(entity.getSurname());
		dto.setUid(entity.getUid());
		dto.setClientUid(entity.getClient().getUid());
		return dto;
	}
}
