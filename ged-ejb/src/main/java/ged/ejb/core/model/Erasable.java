package ged.ejb.core.model;

public interface Erasable {

	/**
	 * El no hace autoboxing así que Boolean es un objeto que requiere un get en
	 * lugar de un is.
	 *
	 * @return
	 */
	Boolean getDeleted();

	Boolean isDeleted();

	void setDeleted(Boolean deleted);
}
