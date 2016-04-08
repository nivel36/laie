package ged.ejb.job.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.job.JobDao;
import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;

@Stateless
public class JobServiceImpl implements JobService {

	@Inject
	@Repository
	private JobDao jobOfferDao;

	@Override
	public void deleteJobOffer(final JobOffer jobOffer) {
		this.jobOfferDao.deleteJobOffer(jobOffer);
	}

	@Override
	public List<JobOffer> findAllJobOffers() {
		return this.jobOfferDao.findAllJobOffers();
	}

	@Override
	public List<JobMeeting> findConductedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		return this.jobOfferDao.findConductedJobMeetingsByJobOffer(jobOffer);
	}

	@Override
	public JobOffer findJobOfferById(final long id) {
		return this.jobOfferDao.findJobOfferById(id);
	}

	@Override
	public List<JobOffer> findJobOfferByName(final String name) {
		return this.jobOfferDao.findJobOfferByName(name);
	}

	@Override
	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		return this.jobOfferDao.findPlannedJobMeetingsByJobOffer(jobOffer);
	}

	@Override
	public List<JobOffer> fullSearch(final String matching) {
		return this.jobOfferDao.fullSearch(matching);
	}

	@Override
	public List<JobOffer> fullSearchByClientName(final String clientName) {
		return this.jobOfferDao.fullSearchByClientName(clientName);
	}

	@Override
	public List<JobOffer> fullSearchByName(final String name) {
		return this.jobOfferDao.fullSearchByName(name);
	}

	@Override
	public List<JobOffer> fullSearchByNameAndClientName(final String name, final String clientName) {
		return this.jobOfferDao.fullSearchByNameAndClientName(name, clientName);
	}

	@Override
	public void insertJobOffer(final JobOffer jobOffer) {
		this.jobOfferDao.insertJobOffer(jobOffer);
	}

	@Override
	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		return this.jobOfferDao.updateJobOffer(jobOffer);
	}

}
