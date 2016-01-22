package ged.ejb.user;

import javax.persistence.Entity;

import ged.ejb.core.AbstractEntity;

@Entity
public class User extends AbstractEntity {

	private String name;

	private String firstSurename;

	private String secondSurename;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurename() {
		return firstSurename;
	}

	public void setFirstSurename(String firstSurename) {
		this.firstSurename = firstSurename;
	}

	public String getSecondSurename() {
		return secondSurename;
	}

	public void setSecondSurename(String secondSurename) {
		this.secondSurename = secondSurename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
