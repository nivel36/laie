package ged.ejb.core.document;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;

@Entity
@Table(name = "DOCUMENT_TEMPLATE", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "language" }) })
public class DocumentTemplate extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private String language;

	@NotNull
	@Column(nullable = false)
	private String name;

	@Lob
	@NotNull
	@Column(nullable = false)
	private String text;

	@NotNull
	@Column(nullable = false)
	private String title;

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

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.language);
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
	public String toString() {
		return this.name + " (" + this.language + ")";
	}

}
