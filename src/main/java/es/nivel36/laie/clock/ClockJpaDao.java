package es.nivel36.laie.clock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.AbstractDao;
import es.nivel36.laie.core.service.Repository;

@Repository
public class ClockJpaDao extends AbstractDao {

	public List<ClockRecord> findClockRecords(final String userUid, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		Objects.requireNonNull(userUid);
		final TypedQuery<ClockRecord> q = this.entityManager.createNamedQuery("ClockRecord.findClockRecord",
				ClockRecord.class);
		q.setParameter("uid", userUid);
		q.setParameter("start", startDateTime);
		q.setParameter("end", endDateTime);
		return q.getResultList();
	}
	
	public void insert(ClockRecord clockRecord ) {
		this.entityManager.persist(clockRecord);
	}
}
