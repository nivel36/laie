package es.nivel36.laie.ejb.statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class CandidateStaticsService extends AbstractStaticsService {

	public long countCandidates(final LocalDate start, final LocalDate end) {
		final LocalDateTime startDate = start.atStartOfDay();
		final LocalDateTime endDate = end.atStartOfDay();
		final String jpql = """
				SELECT COUNT(a) 
				FROM Action a 
				WHERE a.type = 'CREATE' 
				AND a.entityName = 'CANDIDATE' 
				AND a.date <= :endDate 
				AND a.date > :startDate
				"""; 
		final TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("endDate", endDate);
		query.setParameter("startDate", startDate);
		return query.getSingleResult();
	}

	public double getCandidatesPercentageChange(final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));
		
		long lastMonth = this.countCandidates(startDate, start);
		long today = this.countCandidates(start, end);

		return calculatePercentageChange(lastMonth, today);
	}

	public long countUsersCandidates(final User user, final LocalDate start, final LocalDate end) {
		final LocalDateTime startDate = start.atStartOfDay();
		final LocalDateTime endDate = end.atStartOfDay();
		final String jpql = """
				SELECT COUNT(a) 
				FROM Action a 
				WHERE a.type = 'CREATE' 
				AND a.entityName = 'CANDIDATE' 
				AND a.date <= :endDate 
				AND a.date > :startDate 
				AND a.user = :user
				"""; 
		final TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("endDate", endDate);
		query.setParameter("startDate", startDate);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public double getUsersCandidatesPercentageChange(final User user, final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countCandidates(startDate, start);
		long today = this.countCandidates(start, end);

		return calculatePercentageChange(lastMonth, today);
	}
}
