package es.nivel36.laie.ejb.app;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class StatisticPanel extends AbstractEntity {

	@Column(name = "POSITION", nullable = false)
	private int position;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private StatisticPanelType type;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "PERIODICITY", nullable = false)
	private StatisticsPeriodicity periodicity;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public StatisticPanelType getType() {
		return type;
	}

	public void setType(StatisticPanelType type) {
		this.type = type;
	}

	public StatisticsPeriodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(StatisticsPeriodicity periodicity) {
		this.periodicity = periodicity;
	}
}
