package es.nivel36.laie.ejb.curriculum.jobexperience;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class JobExperienceDto implements Serializable, Comparable<JobExperienceDto> {

	private static final long serialVersionUID = 8799220012870925885L;

	private String companyName;

	private String description;

	private YearMonth endDate;

	private String jobPosition;

	private YearMonth startDate;

	private boolean stillWorking;

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
		final JobExperienceDto other = (JobExperienceDto) obj;
		return Objects.equals(this.companyName, other.companyName)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.startDate, other.startDate)
				&& Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.endDate, other.endDate);
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getDescription() {
		return this.description;
	}

	public YearMonth getEndDate() {
		return this.endDate;
	}

	public String getJobPosition() {
		return this.jobPosition;
	}

	public Long getMonthsWorked() {
		Objects.requireNonNull(this.startDate);
		if ((!this.stillWorking) && (this.endDate == null)) {
			throw new IllegalStateException();
		}
		if (this.stillWorking) {
			return ChronoUnit.MONTHS.between(this.startDate, LocalDate.now());
		} else {
			return ChronoUnit.MONTHS.between(this.startDate, this.endDate);
		}
	}

	public YearMonth getStartDate() {
		return this.startDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.companyName, this.description, this.startDate, this.jobPosition, this.stillWorking,
				this.endDate);
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

	public void setEndDate(final YearMonth endDate) {
		this.endDate = endDate;
	}

	public void setJobPosition(final String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public void setStartDate(final YearMonth startDate) {
		this.startDate = startDate;
	}

	public void setStillWorking(final boolean stillWorking) {
		this.stillWorking = stillWorking;
	}

	@Override
	public int compareTo(JobExperienceDto jobExperience) {
		final int startDateCompareTo = this.startDate.compareTo(jobExperience.startDate);
		if (startDateCompareTo != 0) {
			return -startDateCompareTo;
		}
		final int endDateCompareTo = this.endDate.compareTo(jobExperience.endDate);
		if (endDateCompareTo != 0) {
			return -endDateCompareTo;
		}
		final int comapanyNameCompareTo = this.companyName.compareTo(jobExperience.companyName);
		if (comapanyNameCompareTo != 0) {
			return comapanyNameCompareTo;
		}
		final int jobPositionCompareTo = this.jobPosition.compareTo(jobExperience.jobPosition);
		if (jobPositionCompareTo != 0) {
			return jobPositionCompareTo;
		}
		final int descriptionCompareTo = this.description.compareTo(jobExperience.description);
		if (descriptionCompareTo != 0) {
			return descriptionCompareTo;
		}
		return 0;
	}
}
