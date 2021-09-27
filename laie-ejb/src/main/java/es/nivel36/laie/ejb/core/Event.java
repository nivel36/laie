package es.nivel36.laie.ejb.core;

import java.time.LocalDateTime;

import es.nivel36.laie.ejb.user.User;

public interface Event {

	EventType getType();
	
	EventState getState();

	User getUser();

	LocalDateTime getDate();
}
