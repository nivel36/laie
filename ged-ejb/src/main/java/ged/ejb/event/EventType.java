package ged.ejb.event;

public enum EventType {

	EMAIL("event_type.email", "fa-envelope"), //
	MEETING("event_type.meeting", "fa-pencil"), //
	MESSAGE("event_type.message", "fa-envelope"), //
	OTHER("event_type.other", "fa-pencil"), //
	PHONE_CALLL("event_type.phone_call", "fa-phone"), //
	VIDEO_CALLL("event_type.video_call", "fa-computer");

	private String icon;

	private String name;

	EventType(final String name, final String icon) {
		this.name = name;
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getName() {
		return this.name;
	}

}
