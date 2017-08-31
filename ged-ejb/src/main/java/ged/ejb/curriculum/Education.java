package ged.ejb.curriculum;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
		return Objects.hash(degree, description, fromDate, stillStudying, toDate);
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
		return Objects.equals(this.degree, other.degree) && Objects.equals(this.description, other.description)
				&& Objects.equals(this.fromDate, other.fromDate)
				&& Objects.equals(this.stillStudying, other.stillStudying) && Objects.equals(this.toDate, other.toDate);
	}

	@Override
	public String toString() {
		return "Education [degree=" + degree + ", description=" + description + ", fromDate=" + fromDate + ", school="
				+ school + ", stillStudying=" + stillStudying + ", toDate=" + toDate + "]";
	}
}
