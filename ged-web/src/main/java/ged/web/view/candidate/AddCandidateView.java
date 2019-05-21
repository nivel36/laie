package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7552627372149481763L;

	@Override
	public Candidate buildNewCandidate() {
		final Candidate newCandidate = new Candidate();
		final Address address = new Address();
		newCandidate.setAddress(address);
		newCandidate.setOwner(this.sessionUser.get());
		return newCandidate;
	}

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.candidate = this.buildNewCandidate();
		for (final Tag tag : this.candidate.getTags()) {
			this.getTags().add(tag.getLabel());
		}
	}

	public String save() {
		logger.debug("Create new candidate action performed");
		this.candidate.setTags(this.getTagsFromStringList(this.getTags()));
		this.candidate = this.candidateService.save(this.candidate);
		return this.candidateUrl();
	}
}
