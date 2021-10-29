package es.nivel36.laie.ejb.client;

import es.nivel36.laie.ejb.core.model.AddressMerger;
import es.nivel36.laie.ejb.core.model.Merger;

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
