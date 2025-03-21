package es.nivel36.laie.ejb.statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class CommunicationStaticsService extends AbstractStaticsService {

	public long countMessages(final LocalDate start, final LocalDate end) {
		final LocalDateTime startDate = start.atStartOfDay();
		final LocalDateTime endDate = end.atStartOfDay();
		final String sql = "SELECT COUNT(j) FROM JobSubmissionEvent j WHERE j.type = 'EMAIL' OR j.type = 'MESSAGE' OR j.type = 'PHONE_CALL' OR j.type = 'VIDEO_CALL' AND j.date < :endDate AND j.date > :startDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	public double getMessagesPercentageChange(final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countMessages(startDate, start);
		long today = this.countMessages(start, end);

		return calculatePercentageChange(lastMonth, today);
	}

	public long countUsersMessages(final User user, final LocalDate start, final LocalDate end) {
		final LocalDateTime startDate = start.atStartOfDay();
		final LocalDateTime endDate = end.atStartOfDay();
		final String sql = "SELECT COUNT(j) FROM JobSubmissionEvent j WHERE j.type = 'EMAIL' OR j.type = 'MESSAGE' OR j.type = 'PHONE_CALL' OR j.type = 'VIDEO_CALL' AND j.date < :endDate AND j.date > :startDate and j.user = :user";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public double getUsersMessagesPercentageChange(final User user, final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countMessages(startDate, start);
		long today = this.countMessages(start, end);

		return calculatePercentageChange(lastMonth, today);
	}
}
