package es.nivel36.laie.ejb.statistics;

import java.time.LocalDate;
import java.time.Period;

import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class JobOfferStatisticsService extends AbstractStaticsService {

	public long countActiveJobOffers(final LocalDate start, final LocalDate end) {
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.openDate <= :end AND (j.completionDate IS NULL OR j.completionDate > :start)";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return query.getSingleResult();
	}

	public double getActiveJobOfferPercentageChange(final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countActiveJobOffers(startDate, start);
		long today = this.countActiveJobOffers(start, end);

		return calculatePercentageChange(lastMonth, today);
	}

	public long countClosedJobOffers(final LocalDate start, final LocalDate end) {
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.completionDate <= :end AND j.completionDate > :start";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return query.getSingleResult();
	}

	public double getClosedJobOfferPercentageChange(final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countClosedJobOffers(startDate, start);
		long today = this.countClosedJobOffers(start, end);

		return calculatePercentageChange(lastMonth, today);
	}

	public long countUsersActiveJobOffers(final User user, final LocalDate start, final LocalDate end) {

		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.openDate <= :end AND (j.completionDate IS NULL OR j.completionDate > :start and j.owner = :user)";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public double getUsersActiveJobOfferPercentageChange(final User user, final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countActiveJobOffers(startDate, start);
		long today = this.countActiveJobOffers(start, end);

		return calculatePercentageChange(lastMonth, today);
	}

	public long countUsersClosedJobOffers(final User user, final LocalDate start, final LocalDate end) {
		final String sql = "SELECT COUNT(j) FROM JobOffer j WHERE j.completionDate <= :end AND j.completionDate > :start AND j.owner = :user";
		final TypedQuery<Long> query = em.createQuery(sql, Long.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public double getUsersClosedJobOfferPercentageChange(final User user, final LocalDate start, final LocalDate end) {
		final LocalDate startDate = start.minus(Period.between(start, end));

		long lastMonth = this.countClosedJobOffers(startDate, start);
		long today = this.countClosedJobOffers(start, end);

		return calculatePercentageChange(lastMonth, today);
	}
}
