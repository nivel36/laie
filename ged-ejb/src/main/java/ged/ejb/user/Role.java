package ged.ejb.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 5722113796215191203L;

	@Column(length = 32, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "parentRoleId", nullable = true)
	private Role parentRole;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Role other = (Role) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public String getName() {
		return this.name;
	}

	public Role getParentRole() {
		return this.parentRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setParentRole(final Role parentRole) {
		this.parentRole = parentRole;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
