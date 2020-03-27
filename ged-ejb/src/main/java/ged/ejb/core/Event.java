package ged.ejb.core;

import java.time.LocalDateTime;

import ged.ejb.user.User;

public interface Event {

	String getEventType();

	User getUser();

	LocalDateTime getEventDate();
}
