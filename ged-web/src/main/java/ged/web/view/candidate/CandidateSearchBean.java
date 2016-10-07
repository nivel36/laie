package ged.web.view.candidate;

import java.util.List;
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

	private transient final static Logger logger = Logger.getLogger(CandidateSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private final transient CandidateService candidateService;

	private String name;

	private Paginator<Candidate> paginator;

	private String surename;

	@Inject
	public CandidateSearchBean(final CandidateService candidateService) {
		if (candidateService == null) {
			throw new NullPointerException();
		}
		this.candidateService = candidateService;
	}

	public void clean() {
		this.surename = null;
		this.name = null;
		search();
	}

	public String edit(final Candidate candidate) {
		this.flash.put("candidate", candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public String getName() {
		return this.name;
	}

	public Paginator<Candidate> getPaginator() {
		return this.paginator;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		this.paginator.setEntities(this.candidateService.searchByNameAndSurename(this.name, this.surename));
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(final Candidate candidate) {
		if (candidate == null) {
			throw new NullPointerException();
		}
		this.candidateService.delete(candidate);
		search();
	}

	public void search() {
		CandidateSearchBean.logger.fine("Searching for candidates");
		final List<Candidate> candidates = this.candidateService.searchByNameAndSurename(this.name, this.surename);
		this.paginator.setEntities(candidates);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}
}