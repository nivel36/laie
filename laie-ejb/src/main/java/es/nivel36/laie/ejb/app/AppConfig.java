package es.nivel36.laie.ejb.app;

import java.util.List;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "APP_CONFIG")
public class AppConfig extends AbstractEntity {

	private static final long serialVersionUID = -959664391460126029L;

	@Column(name = "SHOW_APP_STATISTICS", nullable = false)
	private boolean showAppStatistics;

	@Column(name = "SHOW_USER_STATISTICS", nullable = false)
	private boolean showUserStatistics;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "appConfig", fetch = FetchType.EAGER)
	@OrderBy("position ASC")
	private List<StatisticPanel> statisticsPanels;

	public boolean isShowUserStatistics() {
		return showUserStatistics;
	}

	public void setShowUserStatistics(final boolean showUserStatistics) {
		this.showUserStatistics = showUserStatistics;
	}

	public boolean isShowAppStatistics() {
		return showAppStatistics;
	}

	public void setShowAppStatistics(final boolean showAppStatistics) {
		this.showAppStatistics = showAppStatistics;
	}

	public List<StatisticPanel> getStatisticsPanels() {
		return statisticsPanels;
	}

	public void setStatisticsPanels(final List<StatisticPanel> statisticsPanels) {
		this.statisticsPanels = statisticsPanels;
	}
}
