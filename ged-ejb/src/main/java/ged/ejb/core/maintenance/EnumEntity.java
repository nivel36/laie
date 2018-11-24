package ged.ejb.core.maintenance;

import ged.ejb.core.model.Identifiable;

public interface EnumEntity extends Identifiable {

	String getDescription();

	String getName();

	void setDescription(final String description);

	void setName(final String name);
}