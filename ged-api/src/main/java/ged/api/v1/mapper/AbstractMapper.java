package ged.api.v1.mapper;

import java.util.ArrayList;
import java.util.Collection;

import ged.api.v1.AbstractDto;
import ged.ejb.core.model.AbstractEntity;

public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

	public Collection<E> mapCollectionOfDtos(final Collection<D> dtos) {
		final Collection<E> entities = new ArrayList<>();
		for (final D dto : dtos) {
			final E entity = mapDto(dto);
			entities.add(entity);
		}
		return entities;
	}

	public Collection<D> mapCollectionOfEntities(final Collection<E> entities) {
		final Collection<D> dtos = new ArrayList<>();
		for (final E entity : entities) {
			final D dto = mapEntity(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public abstract E mapDto(D dtos);

	public abstract D mapEntity(final E entities);

}
