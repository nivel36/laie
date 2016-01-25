package ged.ejb.service.curriculum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AuditedEntity;

@Entity
public class Education extends AuditedEntity {

	private static final long serialVersionUID = 5584224215756841045L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false, updatable = false)
	private Curriculum curriculum;

	@NotNull
	@Column(length = 128, nullable = false)
	private String degree;

	@Column(length = 512)
	private String description;

	@Temporal(TemporalType.DATE)
	private Date fromDate;

	@NotNull
	@Column(length = 128, nullable = false)
	private String school;

	private Boolean stillStudying;

	@Temporal(TemporalType.DATE)
	private Date toDate;

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getDegree() {
		return degree;
	}

	public String getDescription() {
		return description;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public String getSchool() {
		return school;
	}

	public Boolean getStillStudying() {
		return stillStudying;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setStillStudying(Boolean stillStudying) {
		this.stillStudying = stillStudying;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result
				+ ((stillStudying == null) ? 0 : stillStudying.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (stillStudying == null) {
			if (other.stillStudying != null)
				return false;
		} else if (!stillStudying.equals(other.stillStudying))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Education [degree=" + degree + ", description=" + description
				+ ", fromDate=" + fromDate + ", school=" + school
				+ ", stillStudying=" + stillStudying + ", toDate=" + toDate
				+ "]";
	}
}
