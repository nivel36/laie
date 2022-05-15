package es.nivel36.laie.ejb.core.event;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.user.User;

public class UpdateEvent extends AbstractEvent {

	public UpdateEvent(final Auditable entity, final User user) {
		super(entity, user);
	}
}