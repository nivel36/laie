package es.nivel36.laie.ejb.core.model;

import java.io.Serializable;

public interface Merger<E extends Serializable, D extends Serializable> {

	void merge(E entity, D dto);
}
