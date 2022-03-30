package es.nivel36.laie.ejb.job.meeting;

import es.nivel36.core.model.Merger;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferDao;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

public class MeetingMerger implements Merger<Meeting, MeetingDto> {

	private JobOfferDao jobOfferDao;

	private UserDao userDao;

	public MeetingMerger(final JobOfferDao jobOfferDao, final UserDao userDao) {
		this.jobOfferDao = jobOfferDao;
		this.userDao = userDao;
	}

	@Override
	public void merge(Meeting entity, MeetingDto dto) {
		entity.setAttendeesEmails(dto.getAttendeesEmails());
		entity.setDatePlanned(dto.getDatePlanned());
		entity.setDuration(dto.getDuration());
		final JobOffer jobOffer = jobOfferDao.findByUid(dto.getJobOffer().getUid());
		entity.setJobOffer(jobOffer);
		entity.setLocation(dto.getLocation());
		entity.setMeetingType(dto.getMeetingType());
		final User user = userDao.findUserByUid(dto.getOwner().getUid());
		entity.setOwner(user);
		entity.setResult(dto.getResult());
		entity.setTitle(dto.getTitle());
	}
}
