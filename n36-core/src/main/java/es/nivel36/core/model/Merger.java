package es.nivel36.core.model;

import java.io.Serializable;

public interface Merger<E extends Serializable, D extends Serializable> {

	void merge(E entity, D dto);
}
