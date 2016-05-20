package ged.web.view.candidate;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractPageBean {

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	private CandidateService candidateService;

	private String email;

	@Inject
	protected transient Logger logger;

	private String name;

	private Paginator<Candidate> paginator;

	private String phoneNumber;

	private String surename;

	public void clean() {
		this.email = null;
		this.surename = null;
		this.name = null;
		this.phoneNumber = null;
		search();
	}

	public String edit(final Candidate candidate) {
		this.flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public String getEmail() {
		return this.email;
	}

	public String getName() {
		return this.name;
	}

	public Paginator<Candidate> getPaginator() {
		return this.paginator;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		this.paginator.setEntities(this.candidateService.findCandidateByNameAndSurename(this.name, this.surename));
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(final Candidate candidate) {
		this.candidateService.deleteCandidate(candidate);
		search();
	}

	public void search() {
		this.logger.fine("Searching for candidates");
		this.paginator.setEntities(this.candidateService.findCandidateByNameAndSurename(this.name, this.surename));
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}
}
