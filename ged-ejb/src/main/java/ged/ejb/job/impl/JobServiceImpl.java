package ged.ejb.job.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.JobDao;
import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;
import ged.ejb.user.User;

@Stateless
public class JobServiceImpl extends AbstratctAuditedService<JobOffer> implements JobService {

	@Inject
	@Repository
	private JobDao jobDao;

	@Override
	public List<JobMeeting> findConductedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		return this.jobDao.findConductedJobMeetingsByJobOffer(jobOffer);
	}

	@Override
	public List<JobOffer> findJobOfferByName(final String name) {
		return this.jobDao.findJobOfferByName(name);
	}

	@Override
	public List<JobOffer> findJobOffersByOwner(final User owner) {
		return this.jobDao.findJobOffersByOwner(owner);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		return this.jobDao.findLastJobOffers(owner);
	}

	@Override
	public List<JobMeeting> findPlannedJobMeetingsByJobOffer(final JobOffer jobOffer) {
		return this.jobDao.findPlannedJobMeetingsByJobOffer(jobOffer);
	}

	@Override
	public List<JobOffer> fullSearch(final String matching) {
		return this.jobDao.fullSearch(matching);
	}

	@Override
	public List<JobOffer> fullSearchByClientName(final String clientName) {
		return this.jobDao.fullSearchByClientName(clientName);
	}

	@Override
	public List<JobOffer> fullSearchByName(final String name) {
		return this.jobDao.fullSearchByName(name);
	}

	@Override
	public List<JobOffer> fullSearchByNameAndClientName(final String name, final String clientName) {
		return this.jobDao.fullSearchByNameAndClientName(name, clientName);
	}

	@Override
	public Dao<Long, JobOffer> getDao() {
		return this.jobDao;
	}
}
