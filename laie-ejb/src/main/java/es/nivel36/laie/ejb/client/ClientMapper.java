package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.Address;
import es.nivel36.laie.ejb.AddressDto;
import es.nivel36.laie.ejb.AddressMapper;
import es.nivel36.core.model.Mapper;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserMapper;

public class ClientMapper implements Mapper<Client, ClientDto> {

	private ContactMapper contactMapper ;

	private UserMapper userMapper;

	private AddressMapper addressMapper;

	public ClientMapper(final FileService fileService) {
		Objects.requireNonNull(fileService);
		contactMapper = new ContactMapper();
		userMapper = new UserMapper(fileService);
		addressMapper = new AddressMapper();
	}

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
		final List<Contact> contacts = entity.getContacts();
		final List<ContactDto> contactsDto = contactMapper.mapList(contacts);
		dto.setContacts(contactsDto);
	}
}
