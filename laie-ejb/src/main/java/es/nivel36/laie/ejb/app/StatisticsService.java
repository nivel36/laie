package es.nivel36.laie.ejb.app;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class StatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);

	private @Inject AppConfigDao appConfigDao;

	public boolean isShowUserStatistics() {
		logger.debug("Check if user statistics are shown");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		return appConfig.isShowUserStatistics();
	}

	public boolean isShowAppStatistics() {
		logger.debug("Check if app statistics are shown");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		return appConfig.isShowAppStatistics();
	}

	public StatisticPanel getStatisticPanel(final int number) {
		logger.debug("Get statistic panel number {}", number);
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		return appConfig.getStatisticsPanels().get(number - 1);
	}

	public void showUserStatistics() {
		logger.debug("Show user statistics");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		final boolean showUserStatistics = appConfig.isShowUserStatistics();
		if (showUserStatistics) {
			logger.trace("User statistics already shown");
			return;
		}
		appConfig.setShowUserStatistics(true);
	}

	public void hideUserStatistics() {
		logger.debug("Hide user statistics");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		final boolean showUserStatistics = appConfig.isShowUserStatistics();
		if (!showUserStatistics) {
			logger.trace("User statistics already hidden");
			return;
		}
		appConfig.setShowUserStatistics(false);
	}

	public void showAppStatistics() {
		logger.debug("Show app statistics");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		final boolean showAppStatistics = appConfig.isShowAppStatistics();
		if (showAppStatistics) {
			logger.trace("App statistics already shown");
			return;
		}
		appConfig.setShowAppStatistics(true);
	}

	public void hideAppStatistics() {
		logger.debug("Hide app statistics");
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		final boolean showAppStatistics = appConfig.isShowAppStatistics();
		if (!showAppStatistics) {
			logger.trace("App statistics already hidden");
			return;
		}
		appConfig.setShowAppStatistics(false);
	}

	public void changeStatisticPanel(final StatisticPanel statisticPanel, final int panelNumber) {
		Objects.requireNonNull(statisticPanel);
		logger.debug("Change statistic panel number {} to {}", panelNumber, statisticPanel);
		final AppConfig appConfig = this.appConfigDao.find(AppConfig.class, 1L);
		appConfig.getStatisticsPanels().set(panelNumber - 1, statisticPanel);
	}

	public void setAppConfigDao(final AppConfigDao appConfigDao) {
		this.appConfigDao = Objects.requireNonNull(appConfigDao);
	}
}
