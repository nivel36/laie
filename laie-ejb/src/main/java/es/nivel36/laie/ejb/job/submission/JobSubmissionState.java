package es.nivel36.laie.ejb.job.submission;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.core.EventState;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.job.offer.JobOfferProcess;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "JOB_SUBMISSION_STATE")
public class JobSubmissionState  extends AbstractEntity implements EventState {

	private static final long serialVersionUID = -1530029544557152044L;

	@Column(name = "APPROVED")
	private boolean approved;

	@Column(name = "BACKGROUND_COLOR")
	private String backgroundColor;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "DECLINED")
	private boolean declined;

    @OneToMany(mappedBy = "destinationState", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transition> destinationTransitions = new HashSet<>();

	@Column(name = "FIRST")
	private boolean first;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "JOB_OFFER_PROCESS_ID", nullable = false)
	private JobOfferProcess jobOfferProcess;

	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private Set<JobSubmission> jobSubmissions = new HashSet<>();

	@FullTextField(name = "_name")
	@KeywordField(name = "name", aggregable = Aggregable.YES)
	@Column(name = "NAME", unique = true)
	private String name;

	@OneToMany(mappedBy = "originState", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transition> originTransitions = new HashSet<>();

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final JobSubmissionState other = (JobSubmissionState) obj;
		return Objects.equals(this.name, other.name);
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getColor() {
		return color;
	}

	public Set<Transition> getDestinationTransitions() {
		return destinationTransitions;
	}

	public JobOfferProcess getJobOfferProcess() {
		return jobOfferProcess;
	}

	public Set<JobSubmission> getJobSubmissions() {
		return jobSubmissions;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Set<Transition> getOriginTransitions() {
		return originTransitions;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	public boolean isApproved() {
		return this.approved;
	}

	public boolean isClosed() {
		return this.isApproved() || this.isDeclined();
	}

	public boolean isDeclined() {
		return this.declined;
	}

	public boolean isFirst() {
		return this.first;
	}

	public void setApproved(final boolean approved) {
		this.approved = approved;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setDeclined(final boolean declined) {
		this.declined = declined;
	}

	public void setDestinationTransitions(Set<Transition> destinationTransitions) {
		this.destinationTransitions = destinationTransitions;
	}

	public void setFirst(final boolean first) {
		this.first = first;
	}

	public void setJobOfferProcess(JobOfferProcess jobOfferProcess) {
		this.jobOfferProcess = jobOfferProcess;
	}

	public void setJobSubmissions(Set<JobSubmission> jobSubmissions) {
		this.jobSubmissions = jobSubmissions;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOriginTransitions(Set<Transition> originTransitions) {
		this.originTransitions = originTransitions;
	}

	@Override
	public String toString() {
		return this.name;
	}
}