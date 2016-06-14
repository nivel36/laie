package ged.ejb.core.model;

public interface Entity<K> {

	K getId();

	void setId(K id);
}
