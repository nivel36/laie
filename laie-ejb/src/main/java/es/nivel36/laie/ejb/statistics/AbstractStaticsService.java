package es.nivel36.laie.ejb.statistics;

import java.util.Objects;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractStaticsService {

	protected @PersistenceContext(unitName = "laie") EntityManager em;

	protected double calculatePercentageChange(long lastMonth, long today) {
		if (lastMonth == 0) {
			return today > 0 ? 100 : 0;
		}

		double percentageChange = ((double) (today - lastMonth) * 100) / lastMonth;
		return Math.round(percentageChange * 100.0) / 100.0;
	}

	public void setEm(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}
}