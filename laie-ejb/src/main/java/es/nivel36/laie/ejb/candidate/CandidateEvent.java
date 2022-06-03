package es.nivel36.laie.ejb.candidate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.AbstractEvent;
import es.nivel36.laie.ejb.core.action.ActionType;
import es.nivel36.laie.ejb.core.action.Auditable;

@Entity
public class CandidateEvent extends AbstractEvent {

	private static final long serialVersionUID = 8501875519449512260L;

	private Candidate candidate;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	private ActionType type;

	public Candidate getCandidate() {
		return candidate;
	}

	@Override
	public Auditable getEntity() {
		return candidate;
	}

	public ActionType getType() {
		return type;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setType(ActionType type) {
		this.type = type;
	}
}
