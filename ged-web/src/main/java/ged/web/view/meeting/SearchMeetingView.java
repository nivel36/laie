package ged.web.view.meeting;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = 1105875525922748690L;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	private transient ScheduleModel schedule;

	public ScheduleModel getSchedule() {
		return schedule;
	}

	@PostConstruct
	public void init() {
		meetings = initMeetings();
		schedule = new LazyScheduleModel();
		for (Meeting meeting : meetings) {
			ScheduleEvent event = new DefaultScheduleEvent(meeting.getDescription(),
					Date.from(meeting.getDatePlanned().toInstant(ZoneOffset.UTC)),
					Date.from(meeting.getDatePlanned().plusMinutes(30).toInstant(ZoneOffset.UTC)));
			schedule.addEvent(event);
		}
	}

	private List<Meeting> initMeetings() {
		List<Meeting> meetingList = meetingService.findMeeting(sessionUser.get(), Page.ALL);
		return meetingList;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
