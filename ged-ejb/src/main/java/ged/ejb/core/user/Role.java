package ged.ejb.core.user;

import javax.persistence.Entity;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 5722113796215191203L;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
