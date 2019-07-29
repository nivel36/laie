package ged.ejb.curriculum;

import java.time.LocalDate;
import java.time.YearMonth;
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
	@Lob
	private String description;

	private YearMonth endDate;

	@NotNull
	@Field
	@Column(length = 256, nullable = false)
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
		final JobExperience other = (JobExperience) obj;
		return Objects.equals(this.companyName, other.companyName) && Objects.equals(this.curriculum, other.curriculum)
				&& Objects.equals(this.description, other.description)
				&& Objects.equals(this.startDate, other.startDate)
				&& Objects.equals(this.jobPosition, other.jobPosition)
				&& Objects.equals(this.stillWorking, other.stillWorking) && Objects.equals(this.endDate, other.endDate);
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
		return Objects.hash(this.companyName, this.curriculum, this.description, this.startDate, this.jobPosition,
				this.stillWorking, this.endDate);
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
}