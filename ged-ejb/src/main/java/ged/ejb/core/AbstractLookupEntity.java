package ged.ejb.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import ged.ejb.core.i18n.I18n;

@MappedSuperclass
public abstract class AbstractLookupEntity extends AbstractEntity {

	private static final long serialVersionUID = 84699572149525433L;

	@I18n
	@Column(length = 64)
	private String name;

	@I18n
	@Column(length = 64)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
