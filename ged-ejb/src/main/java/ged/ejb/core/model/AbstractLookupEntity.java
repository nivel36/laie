package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import ged.ejb.core.i18n.I18n;

@MappedSuperclass
public abstract class AbstractLookupEntity extends AbstractEntity {

	private static final long serialVersionUID = 84699572149525433L;

	@I18n
	@Column(length = 64)
	private String description;

	@I18n
	@Column(length = 64)
	private String name;

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setName(final String name) {
		this.name = name;
	}
}