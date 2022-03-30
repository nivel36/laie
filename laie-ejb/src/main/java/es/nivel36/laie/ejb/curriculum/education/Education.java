package es.nivel36.laie.ejb.curriculum.education;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import es.nivel36.core.model.AbstractEntity;

@Entity
@Indexed
public class Education extends AbstractEntity implements Comparable<Education>{

	private static final long serialVersionUID = 7831977505416700653L;

	@NotNull
	@Column(length = 128, nullable = false)
	private String degree;

	@Field
	@Column(length = 512)
	private String description;

	private Integer endYear;

	@NotNull
	@Column(length = 128, nullable = false)
	private String school;

	private Integer startYear;

	@NotNull
	@Column(nullable = false)
	private boolean stillStudying;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Education other = (Education) obj;
		return Objects.equals(this.degree, other.degree) && Objects.equals(this.description, other.description)
				&& Objects.equals(this.startYear, other.startYear)
				&& Objects.equals(this.stillStudying, other.stillStudying)
				&& Objects.equals(this.endYear, other.endYear);
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
	
	@Override
	public int compareTo(Education education) {
		final int startYearCompareTo = this.startYear.compareTo(education.startYear);
		if (startYearCompareTo != 0) {
			return -startYearCompareTo;
		}
		final int endYearCompareTo = this.endYear.compareTo(education.endYear);
		if (endYearCompareTo != 0) {
			return -endYearCompareTo;
		}
		final int schoolCompareTo = this.school.compareTo(education.school);
		if (schoolCompareTo != 0) {
			return schoolCompareTo;
		}
		final int degreeCompareTo = this.degree.compareTo(education.degree);
		if (degreeCompareTo != 0) {
			return degreeCompareTo;
		}
		final int descriptionCompareTo = this.description.compareTo(education.description);
		if (descriptionCompareTo != 0) {
			return descriptionCompareTo;
		}
		return 0;
	}
}
