package es.nivel36.laie.web.view.candidate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.web.core.IllegalPageStateException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditCandidateView extends AbstractCandidateView {

	private static final long serialVersionUID = -5736997599473134307L;

	private static final Logger logger = LoggerFactory.getLogger(EditCandidateView.class);

	private transient @Inject EditCandidatePermission editCandidatePermission;

	@PostConstruct
	public void init() {
		if (this.candidateId == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} edit init", this.candidateId);
		findCandidate();
		if(this.candidate.getAddress() == null ) {
			this.candidate.setAddress(new Address());
		}
		this.checkEditPermissions();
		this.initTags();
		this.candidateImage = this.candidate.getPicture();
	}

	private void findCandidate() {
		try {
			final Long id = Long.parseLong(candidateId);
			this.candidate = this.candidateService.findAllCandidateData(id);
			if (this.candidate == null) {
				throw new IllegalPageStateException();
			}
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
	}

	private void checkEditPermissions() {
		if (!editCandidatePermission.validate(candidate)) {
			throw new SecurityException();
		}
	}

	private void initTags() {
		this.tags = this.candidate.getTags().stream().map(Tag::getLabel).collect(Collectors.toList());
	}

	public void save() {
		logger.debug("Save candidate action performed");
		try {
			this.normalizeTags();
			this.saveImage();
			this.candidate = this.candidateService.updateCandidate(this.candidate);
			Faces.redirect(this.candidateUrl());
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("candidateForm:email", "candidate.error.email_exists");
		}
	}

	private void normalizeTags() {
		if (this.tags != null) {
			for (final String tag : this.tags) {
				boolean found = false;
				for (final Tag tagFromCandidate : this.candidate.getTags()) {
					if (tagFromCandidate.getLabel().equals(tag)) {
						found = true;
						break;
					}
				}
				if (!found) {
					this.candidate.getTags().add(new Tag(tag));
				}
			}

			Iterator<Tag> tagIterator = this.candidate.getTags().iterator();

			while (tagIterator.hasNext()) {
				final Tag tagFromIterator = tagIterator.next();
				if (!tags.contains(tagFromIterator.getLabel())) {
					tagIterator.remove();
				}
			}
		} else {
			// Los tags pueden ser nulos a pesar de haberse inicializados ya que JSF
			// interpreta colecciones vacias como null
			this.candidate.setTags(new HashSet<>());
		}
	}

	public void setEditCandidatePermission(final EditCandidatePermission editCandidatePermission) {
		Objects.requireNonNull(editCandidatePermission);
		this.editCandidatePermission = editCandidatePermission;
	}
}