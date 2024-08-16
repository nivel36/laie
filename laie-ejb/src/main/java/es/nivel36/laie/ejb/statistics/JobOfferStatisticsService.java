package es.nivel36.laie.ejb.statistics;

import java.time.LocalDate;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class JobOfferStatisticsService extends AbstractStaticsService {

	public long countActiveJobOffers() {
		final LocalDate startDate = this.getEndDate();
		final LocalDate endDate = LocalDate.now();
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.openDate <= :endDate AND (j.completionDate IS NULL OR j.completionDate > :startDate)";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	public double getActiveJobOfferPercentageChange() {
		final LocalDate startDate = this.getStartDate();
		final LocalDate endDate = this.getEndDate();

		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.openDate <= :endDate AND (j.completionDate IS NULL OR j.completionDate > :startDate)";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		long lastMonth = query.getSingleResult();

		long today = this.countActiveJobOffers();

		if (lastMonth == 0) {
			return today > 0 ? 100 : 0;
		}

		double percentageChange = ((double) (today - lastMonth) * 100) / lastMonth;
		return Math.round(percentageChange * 100.0) / 100.0;
	}
	
	public long countClosedJobOffers() {
		final LocalDate startDate = this.getEndDate();
		final LocalDate endDate = LocalDate.now();
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.completionDate <= :endDate AND j.completionDate > :startDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	public double getClosedJobOfferPercentageChange() {
		final LocalDate startDate = this.getStartDate();
		final LocalDate endDate = this.getEndDate();
		
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.completionDate <= :endDate AND j.completionDate > :startDate";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		long lastMonth = query.getSingleResult();

		long today = this.countClosedJobOffers();

		if (lastMonth == 0) {
			return today > 0 ? 100 : 0;
		}

		double percentageChange = ((double) (today - lastMonth) * 100) / lastMonth;
		return Math.round(percentageChange * 100.0) / 100.0;
	}
}
