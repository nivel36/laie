package es.nivel36.laie.ejb.client;

import java.util.Set;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserMapper;

public class ClientMapper implements Mapper<Client, ClientDto> {

	private ContactMapper contactMapper = new ContactMapper();

	private UserMapper userMapper = new UserMapper();
	
	private AddressMapper addressMapper = new AddressMapper();

	@Override
	public ClientDto map(final Client entity) {
		final ClientDto dto = new ClientDto();
		dto.setCif(entity.getCif());
		dto.setDeleted(entity.isDeleted());
		dto.setName(entity.getName());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setUid(entity.getUid());
		mapOwner(entity, dto);
		mapContacts(entity, dto);
		mapAddress(entity, dto);
		return dto;
	}
	
	private void mapAddress(final Client entity, final ClientDto dto) {
		final Address address = entity.getAddress();
		final AddressDto addressDto = addressMapper.map(address);
		dto.setAddress(addressDto);
	}

	private void mapOwner(final Client entity, final ClientDto dto) {
		final User user = entity.getOwner();
		final UserDto userDto = userMapper.map(user);
		final SimpleUserDto simpleUserDto = new SimpleUserDto(userDto);
		dto.setOwner(simpleUserDto);
	}

	private void mapContacts(final Client entity, final ClientDto dto) {
		final Set<Contact> contacts = entity.getContacts();
		final Set<ContactDto> contactsDto = contactMapper.mapSet(contacts);
		dto.setContacts(contactsDto);
	}
}
