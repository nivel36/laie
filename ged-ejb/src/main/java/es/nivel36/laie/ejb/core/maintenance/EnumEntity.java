package es.nivel36.laie.ejb.core.maintenance;

import es.nivel36.laie.ejb.core.model.Identifiable;

public interface EnumEntity extends Identifiable {

	String getDescription();

	String getName();

	void setDescription(final String description);

	void setName(final String name);
}