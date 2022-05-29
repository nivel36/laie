package es.nivel36.laie.web.view.candidate;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.user.DuplicateEmailException;

@Named
@ViewScoped
public class AddCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -3346845253673407579L;

	private static final Logger logger = LoggerFactory.getLogger(AddCandidateView.class);

	private transient @Inject AddCandidatePermission addCandidatePermission;

	@PostConstruct
	public void init() {
		logger.trace("New candidate init");
		this.checkAddPermissions();
		this.candidate = new Candidate();
		this.tags = new ArrayList<>();
	}

	private void checkAddPermissions() {
		if(!addCandidatePermission.validate(null)) {
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Create new candidate action performed");
		try {
			if (this.tags != null) {
				// Los tags pueden ser nulos a pesar de haberse inicializados ya que JSF
				// interpreta colecciones vacias como null
				this.candidate.setTags(this.tags.stream().map(Tag::new).collect(Collectors.toSet()));
			}
			this.candidate.setOwner(sessionUser.get());
			this.candidateService.addCandidate(candidate);
			this.saveImage();
			Faces.redirect(this.candidateUrl());
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("candidateForm:email", "candidate.error.email_exists");
		}
	}

	public void setAddCandidatePermission(final AddCandidatePermission addCandidatePermission) {
		Objects.requireNonNull(addCandidatePermission);
		this.addCandidatePermission = addCandidatePermission;
	}
}
