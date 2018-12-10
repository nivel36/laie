package ged.api.v1.client;

import javax.inject.Inject;

import ged.api.v1.mapper.AbstractMapper;
import ged.ejb.client.Client;
import ged.ejb.core.Address;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

public class ClientMapper implements AbstractMapper<Client, ClientDto> {

	private final UserService userService;

	@Inject
	public ClientMapper(final UserService userService) {
		this.userService = userService;
	}

	private User getUserByUsername(final String username) {
		return this.userService.findUserByEmail(username);
	}

	@Override
	public Client mapDto(final ClientDto dto) {
		if (dto == null) {
			return null;
		}
		final Client entity = new Client();
		entity.setCif(dto.getCif());
		entity.setName(dto.getName());
		entity.setOwner(this.getUserByUsername(dto.getOwner()));
		entity.setPhoneNumber(dto.getPhoneNumber());
		return entity;
	}

	@Override
	public ClientDto mapEntity(final Client entity) {
		if (entity == null) {
			return null;
		}
		final ClientDto dto = new ClientDto();
		dto.setCif(entity.getCif());
		final Address address = entity.getAddress();
		if (address == null) {
			dto.setCity(null);
			dto.setState(null);
		}
		else {
			dto.setCity(address.getCity());
			dto.setState(address.getState());
		}
		dto.setName(entity.getName());
		final User owner = entity.getOwner();
		if (owner == null) {
			dto.setOwner(null);
		}
		else {
			dto.setOwner(owner.getEmail());
		}
		dto.setPhoneNumber(entity.getPhoneNumber());
		return dto;
	}
}