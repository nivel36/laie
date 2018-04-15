package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	private Long candidateId;

	@Inject
	private transient CandidateService candidateService;

	private final List<String> tags = new ArrayList<>();

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Long getCandidateId() {
		return this.candidateId;
	}

	public List<String> getTags() {
		return this.tags;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		logger.trace("CandidateBean init");
		if (this.candidateId == null) {
			throw new PageNotFoundException();
		}
		this.candidate = this.candidateService.find(this.candidateId);
		if (this.candidate == null) {
			throw new PageNotFoundException();
		}
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		for (final Tag tag : this.candidate.getTags()) {
			this.tags.add(tag.getLabel());
		}
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateId(final Long candidateId) {
		this.candidateId = candidateId;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}
}