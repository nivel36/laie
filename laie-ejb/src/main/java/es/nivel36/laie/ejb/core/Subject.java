package es.nivel36.laie.ejb.core;

public interface Subject {

	String getEmail();

	String getName();

	String getSurname();

	default String getFullName() {
		return getName() + " " + getSurname();
	}
}
