package es.nivel36.laie.ejb.curriculum.jobexperience;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Indexed
public class JobExperience extends AbstractEntity implements Comparable<JobExperience> {

	private static final long serialVersionUID = 7016429624624068218L;

	@Field
	@NotNull
	@Column(nullable = false)
	private String companyName;

	@Field
	@Lob
	private String description;

	private Integer endMonth;

	private Integer endYear;

	@NotNull
	@Field
	@Column(nullable = false)
	private String jobPosition;

	private Integer startMonth;

	private Integer startYear;

	private boolean stillWorking;

	@Override
	public int compareTo(final JobExperience jobExperience) {
		if (this.startYear == null) {
			return 1;
		}
		if (jobExperience.startYear == null) {
			return -1;
		}
		final int startYearCompareTo = this.startYear.compareTo(jobExperience.startYear);
		if (startYearCompareTo != 0) {
			return -startYearCompareTo;
		}
		if (this.endYear == null) {
			return 1;
		}
		if (jobExperience.endYear == null) {
			return -1;
		}
		final int endYearCompareTo = this.endYear.compareTo(jobExperience.endYear);
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
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final JobExperience other = (JobExperience) obj;
		return Objects.equals(this.companyName, other.companyName)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.endYear, other.endYear)
				&& Objects.equals(this.startYear, other.startYear);
	}

	public String getCompanyName() {
		return this.companyName;
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
		final YearMonth startDate = YearMonth.of(startYear, startMonth);
		if (this.stillWorking) {
			return ChronoUnit.MONTHS.between(startDate, LocalDate.now());
		} else {
			final YearMonth endDate = YearMonth.of(endYear, endMonth);
			return ChronoUnit.MONTHS.between(startDate, endDate);
		}
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

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public void setJobPosition(final String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public void setStillWorking(final boolean stillWorking) {
		this.stillWorking = stillWorking;
	}
}