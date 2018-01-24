package ged.api.v1.mapper;

import ged.api.v1.AbstractDto;
import ged.ejb.core.model.AbstractEntity;

public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

	public abstract E mapDto(D dto);

	public abstract D mapEntity(final E entity);
}
