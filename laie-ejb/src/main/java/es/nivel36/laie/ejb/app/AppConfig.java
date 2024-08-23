package es.nivel36.laie.ejb.app;

import java.util.List;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class AppConfig extends AbstractEntity{
	
	private static final long serialVersionUID = -959664391460126029L;

	private boolean showAppStatistics = true;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "AppConfigId")
	private List<StatisticPanel> statisticsPanels;

	public boolean isShowAppStatistics() {
		return showAppStatistics;
	}

	public void setShowAppStatistics(boolean showAppStatistics) {
		this.showAppStatistics = showAppStatistics;
	}

	public List<StatisticPanel> getStatisticsPanels() {
		return statisticsPanels;
	}

	public void setStatisticsPanels(List<StatisticPanel> statisticsPanels) {
		this.statisticsPanels = statisticsPanels;
	}
}
