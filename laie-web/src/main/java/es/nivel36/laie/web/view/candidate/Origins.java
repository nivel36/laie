package es.nivel36.laie.web.view.candidate;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.candidate.Origin;

@Named
@ApplicationScoped
public class Origins implements Serializable {

	private static final long serialVersionUID = 2315483647373595440L;

	private transient @Inject CandidateService candidateService;

	private List<Origin> listOfOrigins;

	public List<Origin> getList() {
		return this.listOfOrigins;
	}

	@PostConstruct
	public void init() {
		this.listOfOrigins = this.candidateService.findCandidateOrigins();
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

}