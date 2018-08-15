package ged.ejb.core.maintenance;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@MappedSuperclass
public abstract class AbstractEnumEntity extends AbstractEntity implements EnumEntity {

	private static final long serialVersionUID = 84699572149525433L;

	@I18n
	@Column(length = 64)
	private String description;

	@I18n
	@Column(length = 64)
	private String name;

	
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
		final AbstractEnumEntity other = (AbstractEnumEntity) obj;
		return Objects.equals(this.description, other.description) && Objects.equals(this.name, other.name);
	}

	
	public String getDescription() {
		return this.description;
	}

	
	public String getName() {
		return this.name;
	}

	
	public int hashCode() {
		return Objects.hash(this.description, this.name);
	}

	
	public void setDescription(final String description) {
		this.description = description;
	}

	
	public void setName(final String name) {
		this.name = name;
	}
}