package ged.ejb.event;

import java.util.Objects;

import javax.persistence.Entity;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@Entity(name="EVENT_TYPE")
public class EventType extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String icon;

	@I18n
	private String name;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventType other = (EventType) obj;
		return Objects.equals(this.icon, other.icon) && Objects.equals(this.name, other.name);
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(icon, name);
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}
}
