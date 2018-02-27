package ged.ejb.curriculum;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class Education extends AbstractAuditedEntity {

	private static final long serialVersionUID = 5584224215756841045L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@NotNull
	@Column(length = 128, nullable = false)
	private String degree;

	@Column(length = 512)
	private String description;

	private LocalDate fromDate;

	@NotNull
	@Column(length = 128, nullable = false)
	private String school;

	private Boolean stillStudying;

	private LocalDate toDate;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Education other = (Education) obj;
		return Objects.equals(this.degree, other.degree) && Objects.equals(this.description, other.description)
				&& Objects.equals(this.fromDate, other.fromDate)
				&& Objects.equals(this.stillStudying, other.stillStudying) && Objects.equals(this.toDate, other.toDate);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getDegree() {
		return this.degree;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDate getFromDate() {
		return this.fromDate;
	}

	public String getSchool() {
		return this.school;
	}

	public Boolean getStillStudying() {
		return this.stillStudying;
	}

	public LocalDate getToDate() {
		return this.toDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.degree, this.description, this.fromDate, this.stillStudying, this.toDate);
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setDegree(final String degree) {
		this.degree = degree;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setSchool(final String school) {
		this.school = school;
	}

	public void setStillStudying(final Boolean stillStudying) {
		this.stillStudying = stillStudying;
	}

	public void setToDate(final LocalDate toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "Education [degree=" + this.degree + ", description=" + this.description + ", fromDate=" + this.fromDate + ", school="
				+ this.school + ", stillStudying=" + this.stillStudying + ", toDate=" + this.toDate + "]";
	}
}
