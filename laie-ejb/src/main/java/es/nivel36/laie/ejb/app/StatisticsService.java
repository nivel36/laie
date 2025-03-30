package es.nivel36.laie.ejb.app;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class StatisticsService {

	private @Inject AppConfigDao appConfigDao;

	public boolean isShowUserStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		return appConfig.isShowUserStatistics();
	}

	public boolean isShowAppStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		return appConfig.isShowAppStatistics();
	}

	public StatisticPanel getStatisticPanel(final int number) {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		return appConfig.getStatisticsPanels().get(number - 1);
	}

	public void setAppConfigDao(final AppConfigDao appConfigDao) {
		this.appConfigDao = appConfigDao;
	}

	public void showUserStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		final boolean showUserStatistics = appConfig.isShowUserStatistics();
		if (showUserStatistics) {
			return;
		}
		appConfig.setShowUserStatistics(true);
	}

	public void hideUserStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		final boolean showUserStatistics = appConfig.isShowUserStatistics();
		if (!showUserStatistics) {
			return;
		}
		appConfig.setShowUserStatistics(false);
	}

	public void showAppStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		final boolean showAppStatistics = appConfig.isShowAppStatistics();
		if (showAppStatistics) {
			return;
		}
		appConfig.setShowAppStatistics(true);
	}

	public void hideAppStatistics() {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		final boolean showAppStatistics = appConfig.isShowAppStatistics();
		if (!showAppStatistics) {
			return;
		}
		appConfig.setShowAppStatistics(false);
	}

	public void changeStatisticPanel(final StatisticPanel statisticPanel, final int panelNumber) {
		final AppConfig appConfig = this.appConfigDao.findAppConfig();
		appConfig.getStatisticsPanels().set(panelNumber - 1, statisticPanel);
		appConfigDao.update(appConfig);
	}
}
