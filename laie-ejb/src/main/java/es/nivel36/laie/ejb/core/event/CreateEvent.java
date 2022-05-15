package es.nivel36.laie.ejb.core.event;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.user.User;

public class CreateEvent extends AbstractEvent {

	public CreateEvent(final Auditable entity, final User user) {
		super(entity, user);
	}
}
