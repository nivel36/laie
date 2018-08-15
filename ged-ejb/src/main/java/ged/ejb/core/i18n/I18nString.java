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

	
	public int hashCode() {
		return Objects.hash(key, locale);
	}

	
	public boolean equals(Object obj) {
		if ( obj == null ) {
			return false;
		}
		if (this == obj){
			return true;
		}
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		I18nString other = (I18nString) obj;
		return Objects.equals(this.key, other.key) && Objects.equals(this.locale, other.locale);
	}

	
	public String toString() {
		return text;
	}
}
