package es.nivel36.laie.ejb.statistics;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractStaticsService {

	protected @PersistenceContext(unitName = "laie") EntityManager em;

	private boolean monthStatics;

	public LocalDate getStartDate() {
		final LocalDate now = LocalDate.now();
		if (monthStatics) {
			return now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
		}
		return now.minusDays(60);
	}

	public LocalDate getEndDate() {
		final LocalDate now = LocalDate.now();
		if (monthStatics) {
			return now.with(TemporalAdjusters.firstDayOfMonth());
		}
		return now.minusDays(30);
	}
}