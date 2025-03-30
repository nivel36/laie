package es.nivel36.laie.ejb.curriculum;

import java.util.Objects;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "EDUCATION")
public class Education extends AbstractEntity implements Comparable<Education> {

	private static final long serialVersionUID = 7831977505416700653L;

	@ManyToOne
	@JoinColumn(name = "CURRICULUM_ID")
	private Curriculum curriculum;

	@NotNull
	@Column(name = "DEGREE", nullable = false)
	private String degree;

	@FullTextField
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Column(name = "SCHOOL", nullable = false)
	private String school;

	@Column(name = "END_YEAR", nullable = false)
	private Integer endYear;

	@Column(name = "START_YEAR", nullable = false)
	private Integer startYear;

	@NotNull
	@Column(name = "STILL_STUDYING", nullable = false)
	private boolean stillStudying;

	@Override
	public int compareTo(final Education education) {
		if (this.startYear == null) {
			return 1;
		}
		if (education.startYear == null) {
			return -1;
		}
		final int startYearCompareTo = this.startYear.compareTo(education.startYear);
		if (startYearCompareTo != 0) {
			return -startYearCompareTo;
		}
		if (this.endYear == null) {
			return 1;
		}
		if (education.endYear == null) {
			return -1;
		}
		final int endYearCompareTo = this.endYear.compareTo(education.endYear);
		if (endYearCompareTo != 0) {
			return -endYearCompareTo;
		}
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		final Education other = (Education) obj;
		return Objects.equals(this.degree, other.degree) && Objects.equals(this.description, other.description)
				&& Objects.equals(this.startYear, other.startYear)
				&& Objects.equals(this.stillStudying, other.stillStudying)
				&& Objects.equals(this.endYear, other.endYear);
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getDegree() {
		return this.degree;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	public String getSchool() {
		return this.school;
	}

	public Integer getStartYear() {
		return this.startYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.degree, this.description, this.startYear, this.stillStudying, this.endYear);
	}

	public boolean isStillStudying() {
		return this.stillStudying;
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

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setSchool(final String school) {
		this.school = school;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setStillStudying(final boolean stillStudying) {
		this.stillStudying = stillStudying;
	}
}
