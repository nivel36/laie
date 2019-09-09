package ged.ejb.event;

import java.util.Objects;

import javax.persistence.Entity;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@Entity(name="EVENT_TYPE")
public class EventType extends AbstractEntity {

	private static final long serialVersionUID = 1723624254313067783L;

	@I18n
	private String name;

	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int hashCode() {
		return Objects.hash(icon, name);
	}

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
}
