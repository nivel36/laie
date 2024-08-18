package es.nivel36.laie.ejb.statistics;

import java.time.LocalDateTime;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class CandidateStaticsService extends AbstractStaticsService {

	public long countCandidates() {
		final String sql = "SELECT COUNT(c) FROM Candidate c";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		return query.getSingleResult();
	}

	public double getCandidatesPercentageChange() {
		final LocalDateTime endDate = this.getEndDate().atStartOfDay();
		final String sql = "SELECT COUNT(a) FROM Action a WHERE a.type = 'CREATE' AND a.entityName = 'CANDIDATE' and a.date < :endDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("endDate", endDate);
		long lastMonth = query.getSingleResult();

		long today = this.countCandidates();

		if (lastMonth == 0) {
			return today > 0 ? 100 : 0;
		}

		double percentageChange = ((double) (today - lastMonth) * 100) / lastMonth;
		return Math.round(percentageChange * 100.0) / 100.0;
	}

}
