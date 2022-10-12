package es.nivel36.laie.ejb.core.document;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Table(name = "DOCUMENT_TEMPLATE", //
		uniqueConstraints = {
				@UniqueConstraint(name = "UQ_DOCUMENT_TEMPLATE_NAME_LANGUAGE", columnNames = { "NAME", "LANGUAGE" }) }, //
		indexes = { @Index(name = "UX_DOCUMENT_TEMPLATE_NAME_LANGUAGE", columnList = "NAME, LANGUAGE", unique = true) })
public class DocumentTemplate extends AbstractEntity {

	private static final long serialVersionUID = 5175582851805624309L;

	@NotNull
	@Column(name = "LANGUAGE", nullable = false)
	private String language;

	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;

	@NotNull
	@Column(name = "TEXT", columnDefinition = "TEXT", nullable = false)
	private String text;

	@NotNull
	@Column(name = "TITLE", nullable = false)
	private String title;

	public String getLanguage() {
		return this.language;
	}

	public String getName() {
		return this.name;
	}

	public String getText() {
		return this.text;
	}

	public String getTitle() {
		return this.title;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

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
		final DocumentTemplate other = (DocumentTemplate) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.language, other.language);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.language);
	}

	@Override
	public String toString() {
		return this.name + " (" + this.language + ")";
	}
}