package es.nivel36.laie.ejb.curriculum;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import es.nivel36.core.model.AbstractEntity;

@Entity
@Table(name = "CURRICULUM_TEMPLATE")
public class CurriculumTemplate extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	@Lob
	private String css;

	@NotNull
	@Column(nullable = false)
	private String description;

	private String screenshoot;
	
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
		final CurriculumTemplate other = (CurriculumTemplate) obj;
		return Objects.equals(this.title, other.title);
	}

	public String getCss() {
		return this.css;
	}

	public String getDescription() {
		return description;
	}

	public String getScreenshoot() {
		return this.screenshoot;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.title);
		return result;
	}

	public void setCss(final String css) {
		this.css = css;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setScreenshoot(final String screenshoot) {
		this.screenshoot = screenshoot;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
