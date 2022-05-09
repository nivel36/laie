package es.nivel36.laie.ejb.core;

public interface EmailContact {

	String getEmail();

	String getName();

	String getSurname();

	default String getFullName() {
		return getName() + " " + getSurname();
	}
}
