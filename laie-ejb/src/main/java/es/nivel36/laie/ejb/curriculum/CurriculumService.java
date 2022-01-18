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
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.education.EducationDto;
import es.nivel36.laie.ejb.curriculum.education.EducationMerger;
import es.nivel36.laie.ejb.curriculum.export.CurriculumExporter;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceDto;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceMerger;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.language.LanguageDto;
import es.nivel36.laie.ejb.curriculum.language.LenguageMerger;
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

	public void addCurriculum(String candidateUid, CurriculumDto curriculum) {
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		final Curriculum entity = new Curriculum();
		entity.setCandidate(candidate);

		final Set<Skill> skills = new HashSet<Skill>();
		for (final String dto : curriculum.getSkills()) {
			Skill skill = this.curriculumDao.findSkill(dto);
			if (skill == null) {
				skill = new Skill();
				skill.setName(dto);
			}
			skills.add(skill);
		}

		final Set<JobExperience> jobExperiences = new HashSet<JobExperience>();
		for (final JobExperienceDto jobExperienceDto : curriculum.getJobExperiences()) {
			final JobExperience jobExperience = new JobExperience();
			new JobExperienceMerger().merge(jobExperience, jobExperienceDto);
			jobExperiences.add(jobExperience);
		}

		final Set<Education> educations = new HashSet<Education>();
		for (final EducationDto educationDto : curriculum.getEducation()) {
			final Education education = new Education();
			new EducationMerger().merge(education, educationDto);
			educations.add(education);
		}

		final Set<Language> languages = new HashSet<Language>();
		for (final LanguageDto languageDto : curriculum.getLanguages()) {
			Language language = new Language();
			new LenguageMerger().merge(language, languageDto);
			languages.add(language);
		}
		this.curriculumDao.insert(entity);
	}
	
	public void updateCurriculum(final String candidateUid, final CurriculumDto curriculum) {
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		final Curriculum oldCurriculum = candidate.getCurriculum();
		this.curriculumDao.delete(oldCurriculum);
		this.addCurriculum(candidateUid, curriculum);
	}

	public List<CurriculumTemplate> findCurriculumTemplates() {
		logger.debug("Find curriculum templates");
		return this.curriculumDao.findCurriculumTemplates();
	}

	public CurriculumDto findCandidatesCurriculum(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find curriculum of candidate {}", candidateUid);
		final Curriculum curriculum = this.curriculumDao.findByCandidateUid(candidateUid);
		return new CurriculumMapper().map(curriculum);
	}

	public CurriculumDto findCurriculumByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find curriculum by uid {}", uid);
		final Curriculum curriculum = this.curriculumDao.findByUid(uid);
		return new CurriculumMapper().map(curriculum);
	}

	public File export(String curriculumUid, CurriculumTemplate template) {
		Objects.requireNonNull(curriculumUid);
		Objects.requireNonNull(template);
		logger.debug("Export curriculum {} with template", curriculumUid, template);
		final Curriculum curriculum = this.curriculumDao.findByUid(curriculumUid);
		return exporter.export(curriculum, template);
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}
}