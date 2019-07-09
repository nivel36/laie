package ged.web.view;

import java.util.Objects;

import ged.ejb.person.Person;

public class SimplePerson extends Person {

	private static final long serialVersionUID = -6155115179930375658L;
	
	SimplePerson() {}
	
	SimplePerson(String email) {
		Objects.requireNonNull(email);
		this.email= email;
	}
}
