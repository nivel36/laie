package es.nivel36.laie.ejb.core.event;

import java.time.LocalDateTime;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.user.User;

public interface Event {

	Auditable getEntity();

	LocalDateTime getDateTime();

	User getUser();

}
