package es.nivel36.laie.ejb.curriculum;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.curriculum.export.CurriculumExporter;
import es.nivel36.laie.ejb.curriculum.skill.Skill;

@Stateless
public class CurriculumService {

	private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

	@Inject
	@Repository
	private CurriculumDao curriculumDao;

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private CurriculumExporter exporter;

	public void addCurriculum(Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		for (final Skill skill : curriculum.getSkills()) {
			Skill skillInDatabase = this.curriculumDao.findSkill(skill.getName());
			if (skill != null) {
				curriculum.removeSkill(skill);
				curriculum.addSkill(skillInDatabase);
			}
		}
		this.curriculumDao.insert(curriculum);
	}

	public void updateCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		this.curriculumDao.update(curriculum);
	}

	public List<CurriculumTemplate> findCurriculumTemplates() {
		logger.debug("Find curriculum templates");
		return this.curriculumDao.findCurriculumTemplates();
	}

	public Curriculum findCandidatesCurriculum(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find curriculum of candidate {}", candidate);
		return this.curriculumDao.findByCandidate(candidate);
	}

	public Curriculum findCurriculumById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find curriculum by id {}", id);
		return this.curriculumDao.find(Curriculum.class, id);
	}

	public File export(final Curriculum curriculum, final CurriculumTemplate template) {
		Objects.requireNonNull(curriculum);
		Objects.requireNonNull(template);
		logger.debug("Export curriculum {} with template", curriculum, template);
		return exporter.export(curriculum, template);
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}
}