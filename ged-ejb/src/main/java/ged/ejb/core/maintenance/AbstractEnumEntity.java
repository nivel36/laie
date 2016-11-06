package ged.ejb.core.maintenance;

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

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}
}