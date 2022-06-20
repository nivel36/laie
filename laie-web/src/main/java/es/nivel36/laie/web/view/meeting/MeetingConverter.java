package es.nivel36.laie.web.view.meeting;

import java.util.Objects;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.web.core.AbstractConverter;

@FacesConverter(managed = true, forClass = Meeting.class)
public class MeetingConverter extends AbstractConverter<Meeting> {

	private @Inject MeetingService meetingService;

	@Override
	protected Meeting getAsObject(Long id) {
		return meetingService.findMeetingById(id);
	}

	public void setUserService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}
}
