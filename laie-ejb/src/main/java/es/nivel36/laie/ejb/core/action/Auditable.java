package es.nivel36.laie.ejb.core.action;

import es.nivel36.laie.ejb.core.model.Identifiable;

public interface Auditable extends Identifiable {

	String getEntityName();

	String getEntityTitle();
}