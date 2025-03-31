package es.nivel36.laie.ejb.curriculum;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Indexed
@Table(name = "JOB_EXPERIENCE")
public class JobExperience extends AbstractEntity implements Comparable<JobExperience> {

	private static final long serialVersionUID = 5196582360914455397L;

	@ManyToOne
	@JoinColumn(name = "CURRICULUM_ID")
	private Curriculum curriculum;

	@FullTextField
	@NotBlank
	@Column(name = "COMPANY_NAME", nullable = false, length = 128)
	private String companyName;

	@FullTextField
	@Lob
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@Column(name = "END_MONTH", scale = 0, precision = 2)
	private Integer endMonth;

	@Column(name = "END_YEAR", scale = 0, precision = 4)
	private Integer endYear;

	@NotBlank
	@FullTextField
	@Column(name = "JOB_POSITION", nullable = false)
	private String jobPosition;

	@Column(name = "START_MONTH", scale = 0, precision = 2)
	private Integer startMonth;

	@Column(name = "START_YEAR", scale = 0, precision = 4)
	private Integer startYear;

	@Column(name = "STILL_WORKING")
	private boolean stillWorking;

	@Override
	public int compareTo(final JobExperience jobExperience) {
		final int startYearCompareTo = this.startYear.compareTo(jobExperience.startYear);
		if (startYearCompareTo != 0) {
			return -startYearCompareTo;
		}
		final int startMonthCompareTo = this.startMonth.compareTo(jobExperience.startMonth);
		if (startMonthCompareTo != 0) {
			return -startMonthCompareTo;
		}
		if (this.stillWorking) {
			if (jobExperience.stillWorking) {
				return 0;
			}
			return -1;
		}
		final int endYearCompareTo = this.endYear.compareTo(jobExperience.endYear);
		if (endYearCompareTo != 0) {
			return -endYearCompareTo;
		}
		final int endMonthCompareTo = this.endMonth.compareTo(jobExperience.endMonth);
		if (endMonthCompareTo != 0) {
			return -endMonthCompareTo;
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
		final JobExperience other = (JobExperience) obj;
		return Objects.equals(this.companyName, other.companyName)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.startYear, other.startYear) && Objects.equals(this.startMonth, other.startMonth)
				&& Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.endYear, other.endYear)
				&& Objects.equals(this.endMonth, other.endMonth);
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public String getJobPosition() {
		return this.jobPosition;
	}

	public Long getMonthsWorked() {
		Objects.requireNonNull(this.startYear);
		Objects.requireNonNull(this.startMonth);
		if (!this.stillWorking && (this.endYear == null || this.endMonth == null)) {
			throw new IllegalStateException();
		}
		final YearMonth startDate = YearMonth.of(startYear, startMonth);
		if (this.stillWorking) {
			return ChronoUnit.MONTHS.between(startDate, LocalDate.now());
		}
		final YearMonth endDate = YearMonth.of(endYear, endMonth);
		return ChronoUnit.MONTHS.between(startDate, endDate);
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public Integer getStartYear() {
		return startYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.companyName, this.description, this.startYear, this.startMonth, this.jobPosition,
				this.stillWorking, this.endYear, this.endMonth);
	}

	public boolean isStillWorking() {
		return this.stillWorking;
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

	public void setStillWorking(final boolean stillWorking) {
		this.stillWorking = stillWorking;
	}
}