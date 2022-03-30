package es.nivel36.laie.ejb.client;

import es.nivel36.core.model.Merger;
import es.nivel36.laie.ejb.AddressMerger;

public class ClientMerger implements Merger<Client, ClientDto> {

	private AddressMerger addressMerger;

	public ClientMerger() {
		addressMerger = new AddressMerger();
	}

	@Override
	public void merge(Client entity, ClientDto dto) {
		addressMerger.merge(entity.getAddress(), dto.getAddress());
		entity.setCif(dto.getCif());
		entity.setName(dto.getName());
		entity.setPhoneNumber(dto.getPhoneNumber());
	}
}
