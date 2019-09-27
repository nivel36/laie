package ged.ejb.core.i18n;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "key", "locale" }))
public class I18nString extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(length = 64, nullable = false)
	private String key;

	@NotNull
	@Column(length = 2, nullable = false)
	private String locale;

	@NotNull
	@Column(length = 1024, nullable = false)
	private String text;

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
		final I18nString other = (I18nString) obj;
		return Objects.equals(this.key, other.key) && Objects.equals(this.locale, other.locale);
	}

	public String getKey() {
		return this.key;
	}

	public String getLocale() {
		return this.locale;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.locale);
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
