package es.nivel36.core.model;

public interface Ownerable {

	Subject getOwner();

	void setOwner(Subject user);
}
