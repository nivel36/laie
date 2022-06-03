package es.nivel36.laie.ejb.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.ejb.core.AbstractEvent;
import es.nivel36.laie.ejb.core.action.ActionType;
import es.nivel36.laie.ejb.core.action.Auditable;

@Entity
public class ClientEvent extends AbstractEvent {

	private static final long serialVersionUID = 8535786524226983756L;

	private Client client;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	private ActionType type;

	public Client getClient() {
		return client;
	}

	@Override
	public ActionType getType() {
		return type;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	@Override
	public Auditable getEntity() {
		return this.client;
	}
}
