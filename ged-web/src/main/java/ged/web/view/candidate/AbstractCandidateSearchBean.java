package ged.web.view.candidate;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

public abstract class AbstractCandidateSearchBean extends AbstractPageBean {

	private static final transient Logger logger = LoggerFactory.getLogger(AbstractCandidateSearchBean.class.getName());

	private static final long serialVersionUID = -5446836478526189391L;

	protected final transient CandidateService candidateService;

	private String name;

	protected Paginator<Candidate> paginator;

	private String position;

	private String surename;

	@Inject
	public AbstractCandidateSearchBean(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void clean() {
		this.surename = null;
		this.name = null;
		this.position = null;
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

	public String getPosition() {
		return this.position;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		search();
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		candidate.setUser(this.sessionBean.getUser());
		this.candidateService.delete(candidate);
		search();
	}

	public void search() {
		logger.debug("Searching for candidates");
		final List<Candidate> candidates = this.candidateService.search(this.name, this.surename, this.position);
		this.paginator.setEntities(candidates);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}
}