package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.core.model.AddressDto;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -3346845253673407579L;

	private static final Logger logger = LoggerFactory.getLogger(AddCandidateView.class);

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.candidate = this.initCandidate();
		this.setTags(new ArrayList<>());
	}

	private CandidateDto initCandidate() {
		final CandidateDto newCandidate = new CandidateDto();
		final AddressDto address = new AddressDto();
		newCandidate.setAddress(address);
		return newCandidate;
	}

	public String save() {
		logger.debug("Create new candidate action performed");
		this.candidate.setTags(this.getTags());
		final String ownerUid = this.sessionUser.get().getUid();
		this.candidate = this.candidateService.addCandidate(candidate, ownerUid);
		return this.candidateUrl();
	}
}
