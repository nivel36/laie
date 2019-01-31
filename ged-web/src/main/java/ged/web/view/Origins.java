package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.CandidateService;
import ged.ejb.candidate.Origin;

@Named
@ApplicationScoped
public class Origins implements Serializable {

	private static final long serialVersionUID = -2279622649333101152L;

	@Inject
	private transient CandidateService candidateService;

	private List<Origin> listOfOrigins;

	public List<Origin> getList() {
		return this.listOfOrigins;
	}

	@PostConstruct
	public void init() {
		this.listOfOrigins = this.candidateService.findAllOrigins();
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

}