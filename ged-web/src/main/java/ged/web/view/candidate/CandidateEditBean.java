package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
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

	private List<String> tagLabels;

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

	public List<String> getTagLabels() {
		return this.tagLabels;
	}

	private Set<Tag> getTagsFromStringList(final List<String> labels) {
		if (labels == null) {
			return new HashSet<>();
		}
		final List<Tag> allTags = this.candidateService.findAllTags();
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			if (label == null) {
				return null;
			}
			boolean found = false;
			for (final Tag tag : allTags) {
				if (tag.getLabel().equals(label)) {
					candidateTags.add(tag);
					found = true;
					break;
				}
			}
			if (!found) {
				final Tag tag = new Tag();
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
		saveCandidate();
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

	public void setTagLabels(final List<String> tagLabels) {
		this.tagLabels = tagLabels;
	}

}