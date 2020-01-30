package ged.ejb.core.document;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ged.ejb.core.model.AbstractEntity;

@Entity
@Table(name = "DOCUMENT_TEMPLATE", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "title" }) })
public class DocumentTemplate extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String language;

	@Column(unique = true)
	private String name;

	private String text;

	private String title;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DocumentTemplate other = (DocumentTemplate) obj;
		return Objects.equals(name, other.name);
	}

	public String getLanguage() {
		return language;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return name;
	}

}
