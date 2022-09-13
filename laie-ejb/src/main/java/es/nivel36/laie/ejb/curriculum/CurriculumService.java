package es.nivel36.laie.ejb.curriculum;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.export.CurriculumExporter;

@Stateless
public class CurriculumService {

	private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

	@Inject
	private CurriculumDao curriculumDao;

	@Inject
	private CurriculumExporter exporter;

	public void addCurriculum(Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		normalizeSkills(curriculum);
		this.curriculumDao.insert(curriculum);
	}

	public Curriculum updateCurriculum(final Curriculum curriculum) {
		Objects.requireNonNull(curriculum);
		normalizeSkills(curriculum);
		return this.curriculumDao.update(curriculum);
	}

	private void normalizeSkills(final Curriculum curriculum) {
		final Set<Skill> skills = curriculum.getSkills();
		final Set<Skill> normlizedSkill = new HashSet<>(skills.size());
		for (final Skill skill : skills) {
			final Skill skillInDatabase = this.curriculumDao.findSkill(skill.getName());
			if (skillInDatabase != null) {
				normlizedSkill.add(skillInDatabase);
			} else {
				normlizedSkill.add(skill);
			}
		}
		curriculum.setSkills(normlizedSkill);
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