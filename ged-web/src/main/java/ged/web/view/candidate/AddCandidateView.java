package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.Address;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7552627372149481763L;
	
	private Candidate initCandidate() {
		final Candidate newCandidate = new Candidate();
		final Address address = new Address();
		newCandidate.setAddress(address);
		newCandidate.setOwner(this.sessionUser.get());
		return newCandidate;
	}

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.candidate = this.initCandidate();
		this.setTags(new ArrayList<>());
	}

	public String save() {
		logger.debug("Create new candidate action performed");
		this.candidate.setTags(this.getTagsFromStringList(this.getTags()));
		this.candidate = this.candidateService.save(this.candidate);
		return this.candidateUrl();
	}
}
