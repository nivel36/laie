package ged.ejb.curriculum.jobexperience;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.curriculum.Curriculum;

@Entity
@Indexed
public class JobExperience extends AbstractEntity {

	private static final long serialVersionUID = -2578992834584255548L;

	@Field
	@NotNull
	@Column(length = 128, nullable = false)
	private String companyName;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@Field
	@NotNull
	@Lob
	@Column(nullable = false)
	private String description;

	private Integer endMonth;

	private Integer endYear;

	@Field
	@Column(length = 256)
	private String jobPosition;

	private Integer startMonth;

	private Integer startYear;

	private Boolean stillWorking;

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
		final JobExperience other = (JobExperience) obj;
		return Objects.equals(this.companyName, other.companyName) && Objects.equals(this.curriculum, other.curriculum)
				&& Objects.equals(this.description, other.description) && Objects.equals(this.startMonth, other.startMonth)
				&& Objects.equals(this.startYear, other.startYear) && Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.endMonth, other.endMonth)
				&& Objects.equals(this.endYear, other.endYear);
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getEndMonth() {
		return this.endMonth;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	public String getJobPosition() {
		return this.jobPosition;
	}

	public int getMonthsWorked() {
		int monthsWorked;
		if (this.stillWorking) {
			monthsWorked = LocalDate.now().getMonth().getValue() - this.startMonth;
		}
		else {
			monthsWorked = this.endMonth - this.startMonth;
		}
		if (monthsWorked < 0) {
			return 12 + monthsWorked;
		}
		else {
			return monthsWorked;
		}
	}

	public Integer getStartMonth() {
		return this.startMonth;
	}

	public Integer getStartYear() {
		return this.startYear;
	}

	public Boolean getStillWorking() {
		return this.stillWorking;
	}

	public int getYearsWorked() {
		if (this.stillWorking) {
			return LocalDate.now().getYear() - this.startYear;
		}
		else {
			return this.endYear - this.startYear;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.companyName, this.curriculum, this.description, this.startMonth, this.startYear, this.jobPosition, this.stillWorking,
				this.endMonth, this.endYear);
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEndMonth(final Integer endMonth) {
		this.endMonth = endMonth;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setJobPosition(final String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public void setStartMonth(final Integer startMonth) {
		this.startMonth = startMonth;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setStillWorking(final Boolean stillWorking) {
		this.stillWorking = stillWorking;
	}
}