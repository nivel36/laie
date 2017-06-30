package ged.ejb.core.maintenance;

import ged.ejb.core.model.Identificable;

public interface EnumEntity extends Identificable<Long> {

	String getDescription();

	String getName();

	void setDescription(final String description);

	void setName(final String name);
}