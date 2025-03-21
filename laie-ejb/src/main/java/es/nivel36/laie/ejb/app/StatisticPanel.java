package es.nivel36.laie.ejb.app;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "STATISTIC_PANEL")
public class StatisticPanel extends AbstractEntity {

	private static final long serialVersionUID = -185584297387973385L;

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

	@ManyToOne
	@JoinColumn(name = "APP_CONFIG_ID")
	private AppConfig appConfig;

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

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
