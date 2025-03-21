package es.nivel36.laie.web.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.app.StatisticPanel;
import es.nivel36.laie.ejb.app.StatisticPanelType;
import es.nivel36.laie.ejb.app.StatisticsPeriodicity;
import es.nivel36.laie.ejb.app.StatisticsService;
import es.nivel36.laie.ejb.statistics.CandidateStaticsService;
import es.nivel36.laie.ejb.statistics.CommunicationStaticsService;
import es.nivel36.laie.ejb.statistics.JobOfferStatisticsService;
import es.nivel36.laie.web.core.view.SessionUser;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class StatisticsViewPart implements Serializable {

	private static final long serialVersionUID = -8256521859429642145L;
	
	private static final Logger logger = LoggerFactory.getLogger(StatisticsViewPart.class);

	private transient @Inject CandidateStaticsService candidateStaticsService;
	private transient @Inject CommunicationStaticsService communicationStaticsService;
	private transient @Inject JobOfferStatisticsService jobOfferStatisticsService;
	private transient @Inject StatisticsService statisticsService;
	private @Inject SessionUser user;

	private boolean showUserStatistics;
	private boolean showAppStatistics;

	private long[] panel;
	private double[] panelPercentageChange;
	private String[] title;

	@PostConstruct
	public void init() {
		logger.trace("Statistics view part init");

		this.showAppStatistics = statisticsService.isShowAppStatistics();
		this.showUserStatistics = statisticsService.isShowUserStatistics();

		panel = new long[8];
		panelPercentageChange = new double[8];
		title = new String[8];
		if (isShowAppStatistics()) {
			for (int j = 1; j < 5; j++) {
				StatisticPanel statisticPanel = statisticsService.getStatisticPanel(j);
				panel[j-1] = getPanel(statisticPanel);
				panelPercentageChange[j-1] = getPanelPercentage(statisticPanel);
				title[j-1] = statisticPanel.getType().name();
			}
		}
		if (isShowUserStatistics()) {
			for (int j = 5; j < 9; j++) {
				StatisticPanel statisticPanel = statisticsService.getStatisticPanel(j);
				panel[j-1] = getPanel(statisticPanel);
				panelPercentageChange[j-1] = getPanelPercentage(statisticPanel);
				title[j-1] = statisticPanel.getType().name();
			}
		}
	}
	
	
	public String getPanelTitle(int number) {
		return title[number-1];
	}
	
	
	public long getPanelValue(int number) {
		return panel[number-1];
	}
	
	public double getPanelPercentageChange(int number) {
		return panelPercentageChange[number-1];
	}

	public long getPanel(StatisticPanel panel) {
		StatisticsPeriodicity period = panel.getPeriodicity();
		LocalDate end = LocalDate.now();
		LocalDate start;
		if (period.equals(StatisticsPeriodicity.CURRENT_MONTH)) {
			start = end.with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.MONTH)) {
			start = end.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.THREE_MONTHS)) {
			start = end.minusMonths(3).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.SIX_MONTHS)) {
			start = end.minusMonths(6).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.YEAR)) {
			start = end.minusYears(1).with(TemporalAdjusters.firstDayOfYear());
		} else {
			throw new IllegalArgumentException("Periodicity value not found");
		}

		if (panel.getType().equals(StatisticPanelType.CANDIDATES)) {
			return candidateStaticsService.countCandidates(start, end);
		} else if (panel.getType().equals(StatisticPanelType.COMMUNICATIONS)) {
			return communicationStaticsService.countMessages(start, end);
		} else if (panel.getType().equals(StatisticPanelType.COMPLETED_JOB_OFFERS)) {
			return jobOfferStatisticsService.countClosedJobOffers(start, end);
		} else if (panel.getType().equals(StatisticPanelType.JOB_OFFERS)) {
			return jobOfferStatisticsService.countActiveJobOffers(start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_CANDIDATES)) {
			return candidateStaticsService.countUsersCandidates(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_COMMUNICATIONS)) {
			return communicationStaticsService.countUsersMessages(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_COMPLETED_JOB_OFFERS)) {
			return jobOfferStatisticsService.countUsersClosedJobOffers(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_JOB_OFFERS)) {
			return jobOfferStatisticsService.countUsersActiveJobOffers(user.get(), start, end);
		}
		throw new IllegalArgumentException("Panel type not found");
	}
	
	public double getPanelPercentage(StatisticPanel panel) {
		StatisticsPeriodicity period = panel.getPeriodicity();
		LocalDate end = LocalDate.now();
		LocalDate start;
		if (period.equals(StatisticsPeriodicity.CURRENT_MONTH)) {
			start = end.with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.MONTH)) {
			start = end.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.THREE_MONTHS)) {
			start = end.minusMonths(3).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.SIX_MONTHS)) {
			start = end.minusMonths(6).with(TemporalAdjusters.firstDayOfMonth());
		} else if (period.equals(StatisticsPeriodicity.YEAR)) {
			start = end.minusYears(1).with(TemporalAdjusters.firstDayOfYear());
		} else {
			throw new IllegalArgumentException("Periodicity value not found");
		}

		if (panel.getType().equals(StatisticPanelType.CANDIDATES)) {
			return candidateStaticsService.getCandidatesPercentageChange(start, end);
		} else if (panel.getType().equals(StatisticPanelType.COMMUNICATIONS)) {
			return communicationStaticsService.getMessagesPercentageChange(start, end);
		} else if (panel.getType().equals(StatisticPanelType.COMPLETED_JOB_OFFERS)) {
			return jobOfferStatisticsService.getClosedJobOfferPercentageChange(start, end);
		} else if (panel.getType().equals(StatisticPanelType.JOB_OFFERS)) {
			return jobOfferStatisticsService.getActiveJobOfferPercentageChange(start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_CANDIDATES)) {
			return candidateStaticsService.getUsersCandidatesPercentageChange(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_COMMUNICATIONS)) {
			return communicationStaticsService.getUsersMessagesPercentageChange(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_COMPLETED_JOB_OFFERS)) {
			return jobOfferStatisticsService.getUsersClosedJobOfferPercentageChange(user.get(), start, end);
		} else if (panel.getType().equals(StatisticPanelType.USERS_JOB_OFFERS)) {
			return jobOfferStatisticsService.getUsersActiveJobOfferPercentageChange(user.get(), start, end);
		}
		throw new IllegalArgumentException("Panel type not found");
	}

	public void setCandidateStaticsService(final CandidateStaticsService candidateStaticsService) {
		Objects.requireNonNull(candidateStaticsService);
		this.candidateStaticsService = candidateStaticsService;
	}

	public void setCommunicationStaticsService(final CommunicationStaticsService communicationStaticsService) {
		Objects.requireNonNull(communicationStaticsService);
		this.communicationStaticsService = communicationStaticsService;
	}

	public void setJobOfferStatisticsService(final JobOfferStatisticsService jobOfferStatisticsService) {
		Objects.requireNonNull(jobOfferStatisticsService);
		this.jobOfferStatisticsService = jobOfferStatisticsService;
	}

	public boolean isShowUserStatistics() {
		return showUserStatistics;
	}

	public boolean isShowAppStatistics() {
		return showAppStatistics;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
}
