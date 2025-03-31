package es.nivel36.laie.ejb.job.submission;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TRANSITION")
public class Transition extends AbstractEntity {

	private static final long serialVersionUID = 419864045653264829L;

	@NotBlank
	@Column(name = "EVENT", nullable = false)
	private String event;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "origin_state_id", nullable = false)
	private JobSubmissionState originState;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "destination_state_id", nullable = false)
	private JobSubmissionState destinationState;

	public Transition() {
	}

	public Transition(final String event, final JobSubmissionState originState,
			final JobSubmissionState destinationState) {
		this.event = Objects.requireNonNull(event);
		this.originState = Objects.requireNonNull(originState);
		this.destinationState = Objects.requireNonNull(destinationState);
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(final String event) {
		this.event = event;
	}

	public JobSubmissionState getOriginState() {
		return originState;
	}

	public void setOriginState(final JobSubmissionState originState) {
		this.originState = originState;
	}

	public JobSubmissionState getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(final JobSubmissionState destinationState) {
		this.destinationState = destinationState;
	}
}