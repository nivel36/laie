package ged.ejb.core.i18n;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.AbstractEntity;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "key", "locale" }))
public class I18nString extends AbstractEntity {

	private static final long serialVersionUID = -945638955619584072L;

	@NotNull
	@Column(length = 64, nullable = false)
	private String key;

	@NotNull
	@Column(length = 2, nullable = false)
	private String locale;

	@NotNull
	@Column(length = 1024, nullable = false)
	private String text;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		I18nString other = (I18nString) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return text;
	}
}
