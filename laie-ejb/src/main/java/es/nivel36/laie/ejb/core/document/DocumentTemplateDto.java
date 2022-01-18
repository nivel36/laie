package es.nivel36.laie.ejb.core.document;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class DocumentTemplateDto implements Serializable {
	
	private static final long serialVersionUID = -4190203148238675748L;

	@NotNull
	private String uid;

	@NotNull
	private String language;

	@NotNull
	private String name;

	@NotNull
	private String text;

	@NotNull
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
	
	public String getUid() {
		return this.uid;
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
	
	public void setUid(final String uid) {
		this.uid = uid;
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
		final DocumentTemplateDto other = (DocumentTemplateDto) obj;
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
