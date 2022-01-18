package es.nivel36.laie.ejb.client;

import es.nivel36.laie.ejb.core.Mapper;

public class SimpleClientMapper implements Mapper<Client, SimpleClientDto> {

	@Override
	public SimpleClientDto map(final Client entity) {
		if (entity == null) {
			return null;
		}
		final SimpleClientDto dto = new SimpleClientDto();
		dto.setCif(entity.getCif());
		dto.setName(entity.getName());
		dto.setUid(entity.getUid());
		return dto;
	}
}
