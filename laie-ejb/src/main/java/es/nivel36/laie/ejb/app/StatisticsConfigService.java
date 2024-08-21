package es.nivel36.laie.ejb.app;

import java.util.List;

import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@Startup
public class StatisticsConfigService {

	private @Inject AppConfigDao appConfigDao;

	private boolean showAppStatistics;
	private List<StatisticPanel> statisticsPanels;

	public void init() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		this.showAppStatistics = appConfig.isShowAppStatistics();
		this.statisticsPanels = appConfig.getStatisticsPanels();
	}

	public void showAppStatistics() {
		if (showAppStatistics) {
			return;
		}
		this.showAppStatistics = true;
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		appConfig.setShowAppStatistics(true);
	}

	public void hideAppStatistics() {
		if (!showAppStatistics) {
			return;
		}
		this.showAppStatistics = false;
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		appConfig.setShowAppStatistics(false);
	}

	public void changeStatisticPanel(final StatisticPanel statisticPanel) {
		this.statisticsPanels.add(statisticPanel.getPosition(), statisticPanel);
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		appConfig.setStatisticsPanels(statisticsPanels);
	}
}
