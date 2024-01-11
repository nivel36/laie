package es.nivel36.laie.web.view.meeting;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;

public class MeetingsByUserLazyDataModel extends LazyDataModel<Meeting> {

	private static final long serialVersionUID = 6084482828895151751L;

	private transient @Inject MeetingService meetingService;

	private User user;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return (int) meetingService.countPlannedMeetings(this.user);
	}

	@Override
	public List<Meeting> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return meetingService.findPlannedMeetings(this.user, Page.of(first, pageSize));
	}

	public void setUser(final User user) {
		Objects.requireNonNull(user);
		this.user = user;
	}
}