package ged.ejb.core.maintenance;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import ged.ejb.core.model.AbstractEntity;

@MappedSuperclass
public abstract class AbstractEnumEntity extends AbstractEntity implements EnumEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 64)
	protected String description;

	@Column(length = 64)
	protected String name;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEnumEntity other = (AbstractEnumEntity) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}