package es.nivel36.laie.web.view;

import java.io.Serializable;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.statistics.CandidateStaticsService;
import es.nivel36.laie.ejb.statistics.CommunicationStaticsService;
import es.nivel36.laie.ejb.statistics.JobOfferStatisticsService;
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
	
	private long activeJobOffers;
	private double activeJobOfferPercentageChange;
	
	private long closedJobOffers;
	private double closedJobOfferPercentageChange;
	
	private long numberOfCandidates;
	private double numberOfCandidatesPercentageChange;
	
	private long numberOfCommunications;
	private double numberOfCommunicationsPercentageChange;
	
	@PostConstruct
	public void init() {
		logger.trace("Statistics view part init");
		
		this.activeJobOffers = this.jobOfferStatisticsService.countActiveJobOffers();
		this.activeJobOfferPercentageChange = this.jobOfferStatisticsService.getActiveJobOfferPercentageChange();
		
		this.closedJobOffers = this.jobOfferStatisticsService.countClosedJobOffers();
		this.closedJobOfferPercentageChange = this.jobOfferStatisticsService.getClosedJobOfferPercentageChange();
		
		this.numberOfCandidates = this.candidateStaticsService.countCandidates();
		this.numberOfCandidatesPercentageChange = this.candidateStaticsService.getCandidatesPercentageChange();
		
		this.numberOfCommunications = this.communicationStaticsService.countMessages();
		this.numberOfCommunicationsPercentageChange = this.communicationStaticsService.getMessagesPercentageChange();
	}
	
	public double getActiveJobOfferPercentageChange() {
		return activeJobOfferPercentageChange;
	}

	public long getActiveJobOffers() {
		return activeJobOffers;
	}

	public double getClosedJobOfferPercentageChange() {
		return closedJobOfferPercentageChange;
	}

	public long getClosedJobOffers() {
		return closedJobOffers;
	}

	public long getNumberOfCandidates() {
		return numberOfCandidates;
	}

	public double getNumberOfCandidatesPercentageChange() {
		return numberOfCandidatesPercentageChange;
	}
	
	public long getNumberOfCommunications() {
		return numberOfCommunications;
	}

	public double getNumberOfCommunicationsPercentageChange() {
		return numberOfCommunicationsPercentageChange;
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
}
