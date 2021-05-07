package ged.ejb.core;

import java.time.LocalDateTime;

import ged.ejb.user.User;

public interface Event {

	EventType getType();
	
	EventState getState();

	User getUser();

	LocalDateTime getDate();
}
