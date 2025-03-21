package es.nivel36.laie.web.view.settings;

import org.omnifaces.util.Faces;

import es.nivel36.laie.ejb.app.StatisticPanelType;
import es.nivel36.laie.ejb.app.StatisticsPeriodicity;
import es.nivel36.laie.ejb.app.StatisticsService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class StatisticsSettingsView extends AbstractView {

	private static final long serialVersionUID = -2452727653812464042L;

	private StatisticPanelType userFirstPanel;
	private StatisticsPeriodicity userFirstPanelPeriodicity;
	private StatisticPanelType userSecondPanel;
	private StatisticsPeriodicity userSecondPanelPeriodicity;
	private StatisticPanelType userThirdPanel;
	private StatisticsPeriodicity userThirdPanelPeriodicity;
	private StatisticPanelType userFourthPanel;
	private StatisticsPeriodicity userFourthPanelPeriodicity;
	private StatisticPanelType appFirstPanel;
	private StatisticsPeriodicity appFirstPanelPeriodicity;
	private StatisticPanelType appSecondPanel;
	private StatisticsPeriodicity appSecondPanelPeriodicity;
	private StatisticPanelType appThirdPanel;
	private StatisticsPeriodicity appThirdPanelPeriodicity;
	private StatisticPanelType appFourthPanel;
	private StatisticsPeriodicity appFourthPanelPeriodicity;
	private boolean showAppStatistics;
	private boolean showUserStatistics;
	private @Inject StatisticsService statisticsService;

	@PostConstruct
	public void init() {
		this.showAppStatistics = statisticsService.isShowAppStatistics();
		this.showUserStatistics = statisticsService.isShowUserStatistics();
		this.appFirstPanel = statisticsService.getStatisticPanel(1).getType();
		this.appFirstPanelPeriodicity = statisticsService.getStatisticPanel(1).getPeriodicity();
		this.appSecondPanel = statisticsService.getStatisticPanel(2).getType();
		this.appSecondPanelPeriodicity = statisticsService.getStatisticPanel(2).getPeriodicity();
		this.appThirdPanel = statisticsService.getStatisticPanel(3).getType();
		this.appThirdPanelPeriodicity = statisticsService.getStatisticPanel(3).getPeriodicity();
		this.appFourthPanel = statisticsService.getStatisticPanel(4).getType();
		this.appFourthPanelPeriodicity = statisticsService.getStatisticPanel(4).getPeriodicity();
		this.userFirstPanel = statisticsService.getStatisticPanel(5).getType();
		this.userFirstPanelPeriodicity = statisticsService.getStatisticPanel(5).getPeriodicity();
		this.userSecondPanel = statisticsService.getStatisticPanel(6).getType();
		this.userSecondPanelPeriodicity = statisticsService.getStatisticPanel(6).getPeriodicity();
		this.userThirdPanel = statisticsService.getStatisticPanel(7).getType();
		this.userThirdPanelPeriodicity = statisticsService.getStatisticPanel(7).getPeriodicity();
		this.userFourthPanel = statisticsService.getStatisticPanel(8).getType();
		this.userFourthPanelPeriodicity = statisticsService.getStatisticPanel(8).getPeriodicity();
	}

	public void save() {
		if (showAppStatistics) {
			this.statisticsService.showAppStatistics();
		} else {
			this.statisticsService.hideAppStatistics();
		}
		if (showUserStatistics) {
			this.statisticsService.showUserStatistics();
		} else {
			this.statisticsService.hideUserStatistics();
		}
		Faces.redirect("/index.xhtml");
	}

	public StatisticPanelType getUserFirstPanel() {
		return userFirstPanel;
	}

	public void setUserFirstPanel(StatisticPanelType userFirstPanel) {
		this.userFirstPanel = userFirstPanel;
	}

	public StatisticsPeriodicity getUserFirstPanelPeriodicity() {
		return userFirstPanelPeriodicity;
	}

	public void setUserFirstPanelPeriodicity(StatisticsPeriodicity userFirstPanelPeriodicity) {
		this.userFirstPanelPeriodicity = userFirstPanelPeriodicity;
	}

	public StatisticPanelType getUserSecondPanel() {
		return userSecondPanel;
	}

	public void setUserSecondPanel(StatisticPanelType userSecondPanel) {
		this.userSecondPanel = userSecondPanel;
	}

	public StatisticsPeriodicity getUserSecondPanelPeriodicity() {
		return userSecondPanelPeriodicity;
	}

	public void setUserSecondPanelPeriodicity(StatisticsPeriodicity userSecondPanelPeriodicity) {
		this.userSecondPanelPeriodicity = userSecondPanelPeriodicity;
	}

	public StatisticPanelType getUserThirdPanel() {
		return userThirdPanel;
	}

	public void setUserThirdPanel(StatisticPanelType userThirdPanel) {
		this.userThirdPanel = userThirdPanel;
	}

	public StatisticsPeriodicity getUserThirdPanelPeriodicity() {
		return userThirdPanelPeriodicity;
	}

	public void setUserThirdPanelPeriodicity(StatisticsPeriodicity userThirdPanelPeriodicity) {
		this.userThirdPanelPeriodicity = userThirdPanelPeriodicity;
	}

	public StatisticPanelType getUserFourthPanel() {
		return userFourthPanel;
	}

	public void setUserFourthPanel(StatisticPanelType userFourthPanel) {
		this.userFourthPanel = userFourthPanel;
	}

	public StatisticsPeriodicity getUserFourthPanelPeriodicity() {
		return userFourthPanelPeriodicity;
	}

	public void setUserFourthPanelPeriodicity(StatisticsPeriodicity userFourthPanelPeriodicity) {
		this.userFourthPanelPeriodicity = userFourthPanelPeriodicity;
	}

	public StatisticPanelType getAppFirstPanel() {
		return appFirstPanel;
	}

	public void setAppFirstPanel(StatisticPanelType appFirstPanel) {
		this.appFirstPanel = appFirstPanel;
	}

	public StatisticsPeriodicity getAppFirstPanelPeriodicity() {
		return appFirstPanelPeriodicity;
	}

	public void setAppFirstPanelPeriodicity(StatisticsPeriodicity appFirstPanelPeriodicity) {
		this.appFirstPanelPeriodicity = appFirstPanelPeriodicity;
	}

	public StatisticPanelType getAppSecondPanel() {
		return appSecondPanel;
	}

	public void setAppSecondPanel(StatisticPanelType appSecondPanel) {
		this.appSecondPanel = appSecondPanel;
	}

	public StatisticsPeriodicity getAppSecondPanelPeriodicity() {
		return appSecondPanelPeriodicity;
	}

	public void setAppSecondPanelPeriodicity(StatisticsPeriodicity appSecondPanelPeriodicity) {
		this.appSecondPanelPeriodicity = appSecondPanelPeriodicity;
	}

	public StatisticPanelType getAppThirdPanel() {
		return appThirdPanel;
	}

	public void setAppThirdPanel(StatisticPanelType appThirdPanel) {
		this.appThirdPanel = appThirdPanel;
	}

	public StatisticsPeriodicity getAppThirdPanelPeriodicity() {
		return appThirdPanelPeriodicity;
	}

	public void setAppThirdPanelPeriodicity(StatisticsPeriodicity appThirdPanelPeriodicity) {
		this.appThirdPanelPeriodicity = appThirdPanelPeriodicity;
	}

	public StatisticPanelType getAppFourthPanel() {
		return appFourthPanel;
	}

	public void setAppFourthPanel(StatisticPanelType appFourthPanel) {
		this.appFourthPanel = appFourthPanel;
	}

	public StatisticsPeriodicity getAppFourthPanelPeriodicity() {
		return appFourthPanelPeriodicity;
	}

	public void setAppFourthPanelPeriodicity(StatisticsPeriodicity appFourthPanelPeriodicity) {
		this.appFourthPanelPeriodicity = appFourthPanelPeriodicity;
	}

	public boolean isShowAppStatistics() {
		return showAppStatistics;
	}

	public void setShowAppStatistics(boolean showAppStatistics) {
		this.showAppStatistics = showAppStatistics;
	}

	public boolean isShowUserStatistics() {
		return showUserStatistics;
	}

	public void setShowUserStatistics(boolean showUserStatistics) {
		this.showUserStatistics = showUserStatistics;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
}
