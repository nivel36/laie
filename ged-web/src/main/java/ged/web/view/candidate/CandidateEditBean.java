package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateEditBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4334616177754425866L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient CurriculumService curriculumService;

	private UIComponent emailField;

	private List<String> tagLabels;

	@Inject
	private transient TagService tagService;

	public String cancel() {
		if (this.candidate.getId() == 0) {
			return "candidateSearch?&faces-redirect=true";
		} else {
			return "candidateView?id=" + this.candidate.getId() + "&faces-redirect=true";
		}
	}

	private Curriculum createNewCurriculum() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setCandidate(this.candidate);
		return curriculum;
	}

	public String editCurriculum() {
		Curriculum curriculum = null;
		if (this.candidate.getId() == 0) {
			this.candidate.setOwner(this.sessionBean.getUser());
			this.candidateService.save(this.candidate);
			curriculum = createNewCurriculum();
		} else {
			this.candidate = this.candidateService.save(this.candidate);
			curriculum = findCurriculum(this.candidate.getId());
		}
		this.flash.put("curriculum", curriculum);
		return "curriculumEdit?faces-redirect=true";
	}

	private Curriculum findCurriculum(final long id) {
		Curriculum curriculum = this.curriculumService.findByCandidateId(id);
		if (curriculum == null) {
			curriculum = createNewCurriculum();
		}
		return curriculum;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public UIComponent getEmailField() {
		return this.emailField;
	}

	public List<String> getTagLabels() {
		return this.tagLabels;
	}

	private Set<Tag> getTagsFromStringList(final List<String> labels) {
		if (labels == null) {
			return new HashSet<>();
		}
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			if (label == null) {
				return null;
			}
			final Tag tag;
			final List<Tag> tagsFoundInDataBase = this.tagService.searchByLabel(label);
			if (tagsFoundInDataBase.size() == 1) {
				tag = tagsFoundInDataBase.get(0);
			} else {
				tag = new Tag();
				tag.setLabel(label);
				candidateTags.add(tag);
			}
		}
		return candidateTags;
	}

	@PostConstruct
	private void init() {
		this.candidate = new Candidate();
		this.candidate.setAddress(new Address());
		this.tagLabels = new ArrayList<>();
	}

	public String save() {
		logger.debug("Save candidate action performed");
		this.candidate.setTags(getTagsFromStringList(this.tagLabels));
		try {
			saveCandidate();
		} catch (final EJBException e) {
			if (e.getCause() instanceof ValidationException) {
				addErrorToField(this.emailField, "candidate.error.email_exist");
				return null;
			}
		}
		return "candidateView.xhtml?id=" + this.candidate.getId() + "&faces-redirect=true";
	}

	private void saveCandidate() {
		if (this.candidate.getOwner() == null) {
			this.candidate.setOwner(this.sessionBean.getUser());
		}
		this.candidate = this.candidateService.save(this.candidate);
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setEmailField(final UIComponent emailField) {
		this.emailField = emailField;
	}

	public void setTagLabels(final List<String> tagLabels) {
		this.tagLabels = tagLabels;
	}

	public void setTagService(final TagService tagService) {
		this.tagService = tagService;
	}
}