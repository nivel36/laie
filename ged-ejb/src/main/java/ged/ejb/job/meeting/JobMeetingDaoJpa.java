package ged.ejb.job.meeting;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractJpaDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobMeetingDaoJpa extends AbstractJpaDao<JobMeeting> implements JobMeetingDao {

	@Inject
	public JobMeetingDaoJpa(final EntityManager em) {
		super(em);
	}

	@Override
	protected Class<JobMeeting> getType() {
		return JobMeeting.class;
	}
}
