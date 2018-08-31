package ged.ejb.user.role;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Role extends AbstractEntity {

	private static final long serialVersionUID = 5722113796215191203L;

	@Column(length = 32, unique = true)
	private String name;

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Role other = (Role) obj;
		return Objects.equals(this.name, other.name);
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}