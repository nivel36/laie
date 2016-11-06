package ged.ejb.core.maintenance;

import ged.ejb.core.model.Entity;

public interface EnumEntity extends Entity<Long> {

	String getDescription();

	String getName();

	void setDescription(final String description);

	void setName(final String name);
}