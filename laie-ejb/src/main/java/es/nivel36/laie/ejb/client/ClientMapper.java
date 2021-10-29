package es.nivel36.laie.ejb.client;

import java.util.Set;

import es.nivel36.laie.ejb.core.Mapper;

public class ClientMapper implements Mapper<Client, ClientDto> {

	private ContactMapper contactMapper = new ContactMapper();

	@Override
	public ClientDto map(final Client entity) {
		final ClientDto dto = new ClientDto();
		dto.setCif(entity.getCif());
		dto.setDeleted(entity.isDeleted());
		dto.setName(entity.getName());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setUid(entity.getUid());
		mapContacts(entity, dto);
		return dto;
	}

	private void mapContacts(final Client entity, final ClientDto dto) {
		final Set<Contact> contacts = entity.getContacts();
		final Set<ContactDto> contactsDto = contactMapper.mapSet(contacts);
		dto.setContacts(contactsDto);
	}
}
