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

public abstract class AbstractCandidateSearchBean extends AbstractPageBean {

	private static final transient Logger logger = LoggerFactory.getLogger(AbstractCandidateSearchBean.class.getName());

	private static final long serialVersionUID = -5446836478526189391L;

	protected List<Candidate> candidates;

	protected final transient CandidateService candidateService;

	private String name;

	private String position;

	protected Candidate selectedCandidate;

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

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public String getName() {
		return this.name;
	}

	public String getPosition() {
		return this.position;
	}

	public Candidate getSelectedCandidate() {
		return this.selectedCandidate;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		search();
	}

	public String newCandidate() {
		return "candidateEdit?faces-redirect=true";
	}

	public void remove(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		this.candidateService.delete(candidate);
		search();
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.name, this.surename, this.position);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	public void setSelectedCandidate(final Candidate selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}
}