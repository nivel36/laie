package es.nivel36.laie.ejb.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Mapper<E extends Serializable, D extends Serializable> {

	public default List<D> mapList(final List<E> entities) {
		if (entities == null) {
			return null;
		}
		final List<D> dtos = new ArrayList<>();
		for (final E entity : entities) {
			final D dto = map(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public default Set<D> mapSet(final Set<E> entities) {
		if (entities == null) {
			return null;
		}
		final Set<D> dtos = new HashSet<>();
		for (final E entity : entities) {
			final D dto = map(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	D map(final E entity);
}