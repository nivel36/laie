package es.nivel36.laie.ejb.statistics;

import java.time.LocalDateTime;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class CommunicationStaticsService extends AbstractStaticsService {

	public long countMessages() {
		final LocalDateTime startDate = this.getEndDate().atStartOfDay();
		final LocalDateTime endDate = LocalDateTime.now();
		final String sql = "SELECT COUNT(j) FROM JobSubmissionEvent j WHERE j.type = 'EMAIL' OR j.type = 'MESSAGE' OR j.type = 'PHONE_CALL' OR j.type = 'VIDEO_CALL' AND j.date < :endDate AND j.date > :startDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	public double getMessagesPercentageChange() {
		final LocalDateTime startDate = this.getStartDate().atStartOfDay();
		final LocalDateTime endDate = this.getEndDate().atStartOfDay();

		final String sql = "SELECT COUNT(j) FROM JobSubmissionEvent j WHERE j.type = 'EMAIL' OR j.type = 'MESSAGE' OR j.type = 'PHONE_CALL' OR j.type = 'VIDEO_CALL' AND j.date < :endDate AND j.date > :startDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		long lastMonth = query.getSingleResult();

		long today = this.countMessages();

		if (lastMonth == 0) {
			return today > 0 ? 100 : 0;
		}

		double percentageChange = ((double) (today - lastMonth) * 100) / lastMonth;
		return Math.round(percentageChange * 100.0) / 100.0;
	}
}
