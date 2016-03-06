package ged.web.view.candidate;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.util.Log;
import ged.web.core.view.AbstractSearchBean;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractSearchBean<Candidate> {

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	private CandidateService candidateService;

	private String email;

	private String name;

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

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getSurename() {
		return this.surename;
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(final Candidate candidate) {
		this.candidateService.deleteCandidate(candidate);
		search();
	}

	@Log
	@Override
	public void search() {
		this.logger.fine("Searching for candidates");
		this.entities = this.candidateService.findCandidateByNameAndSurename(this.name, this.surename);
		trimList();
		setPaginationSize();
	}

	public void setEmail(final String email) {
		this.email = email;
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
