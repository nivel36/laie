package ged.ejb.core.model;

public interface Erasable {

	Boolean getDeleted();

	default Boolean isDeleted() {
		return getDeleted();
	}

	void setDeleted(Boolean deleted);
}
