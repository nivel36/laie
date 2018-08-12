package ged.ejb.curriculum.jobexperience;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

	private LocalDate fromDate;

	@Field
	@Column(length = 256)
	private String jobPosition;

	private Boolean stillWorking;

	private LocalDate toDate;

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
				&& Objects.equals(this.description, other.description) && Objects.equals(this.fromDate, other.fromDate)
				&& Objects.equals(this.jobPosition, other.jobPosition) && Objects.equals(this.stillWorking, other.stillWorking)
				&& Objects.equals(this.toDate, other.toDate);
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

	public LocalDate getFromDate() {
		return this.fromDate;
	}

	public String getJobPosition() {
		return this.jobPosition;
	}

	public long getMonthsWorked() {
		if (this.stillWorking) {
			return ChronoUnit.MONTHS.between(this.fromDate, LocalDate.now());
		}
		else {
			return ChronoUnit.MONTHS.between(this.fromDate, this.toDate);
		}
	}

	public Boolean getStillWorking() {
		return this.stillWorking;
	}

	public LocalDate getToDate() {
		return this.toDate;
	}

	public long getYearsWorked() {
		if (this.stillWorking) {
			return ChronoUnit.YEARS.between(this.fromDate, LocalDate.now());
		}
		else {
			return ChronoUnit.YEARS.between(this.fromDate, this.toDate);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.companyName, this.curriculum, this.description, this.fromDate, this.jobPosition, this.stillWorking, this.toDate);
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

	public void setFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setJobPosition(final String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public void setStillWorking(final Boolean stillWorking) {
		this.stillWorking = stillWorking;
	}

	public void setToDate(final LocalDate toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "JobExperience [companyName=" + this.companyName + ", description=" + this.description + ", fromDate=" + this.fromDate + ", jobPosition="
				+ this.jobPosition + ", stillWorking=" + this.stillWorking + ", toDate=" + this.toDate + "]";
	}
}
