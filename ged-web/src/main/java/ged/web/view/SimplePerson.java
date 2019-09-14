package ged.web.view;

import java.util.Objects;

import ged.ejb.person.Person;

public class SimplePerson extends Person {

	private static final long serialVersionUID = 1L;
	
	SimplePerson() {}
	
	SimplePerson(String email) {
		Objects.requireNonNull(email);
		this.email= email;
	}
}
