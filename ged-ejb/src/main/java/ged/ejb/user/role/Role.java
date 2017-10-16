package ged.ejb.user.role;

import java.util.Objects;

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

	public Role() {
	}

	public Role(final String roleName) {
		this.name = roleName;
	}

	@Override
	public boolean equals(final Object obj) {
		if( obj == null ) {
			return false;
		}
		if (this == obj)  {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Role other = (Role) obj;
		return Objects.equals(this.name, other.name);
	}

	public String getName() {
		return this.name;
	}

	public Role getParentRole() {
		return this.parentRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
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