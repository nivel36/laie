package es.nivel36.laie.ejb.client;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.Merger;

public class ContactMerger implements Merger<Contact, ContactDto> {

	@Override
	public void merge(Contact entity, ContactDto dto) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(dto);
		entity.setEmail(dto.getEmail());
		entity.setLanguage(dto.getLanguage());
		entity.setName(dto.getName());
		entity.setPhoneNumber(dto.getPhoneNumber());
		entity.setPosition(dto.getPosition());
		entity.setSurname(dto.getSurname());
	}
}
