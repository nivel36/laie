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
import es.nivel36.laie.ejb.curriculum.export.CurriculumExporter;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceDto;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.language.LanguageDto;
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

	public void insert(String candidateUid, CurriculumDto curriculum) {
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		final Curriculum entity = new Curriculum();
		entity.setCandidate(candidate);

		final Set<Skill> skills = new HashSet<Skill>();
		for (final String dto : curriculum.getSkills()) {
			Skill skill = this.curriculumDao.findSkill(dto);
			if (skill == null) {
				skill = new Skill();
				skill.setName(dto);
				skill.setCurriculum(entity);
			}
			skills.add(skill);
		}

		final Set<JobExperience> jobExperiences = new HashSet<JobExperience>();
		for (final JobExperienceDto jobExperienceDto : curriculum.getJobExperiences()) {
			final JobExperience jobExperience = new JobExperience();
			jobExperience.setCompanyName(jobExperienceDto.getCompanyName());
			jobExperience.setCurriculum(entity);
			jobExperience.setDescription(jobExperienceDto.getDescription());
			jobExperience.setEndDate(jobExperienceDto.getEndDate());
			jobExperience.setJobPosition(jobExperienceDto.getJobPosition());
			jobExperience.setStartDate(jobExperienceDto.getStartDate());
			jobExperience.setStillWorking(jobExperienceDto.isStillWorking());
			jobExperiences.add(jobExperience);
		}

		final Set<Education> educations = new HashSet<Education>();
		for (final EducationDto educationDto : curriculum.getEducation()) {
			final Education education = new Education();
			education.setCurriculum(entity);
			education.setDegree(educationDto.getDegree());
			education.setDescription(educationDto.getDegree());
			education.setEndYear(educationDto.getEndYear());
			education.setSchool(educationDto.getSchool());
			education.setStartYear(educationDto.getStartYear());
			education.setStillStudying(education.isStillStudying());
			educations.add(education);
		}

		final Set<Language> languages = new HashSet<Language>();
		for (final LanguageDto languageDto : curriculum.getLanguages()) {
			Language language = new Language();
			language.setCurriculum(entity);
			language.setName(languageDto.getName());
			language.setLevel(languageDto.getLevel());
			languages.add(language);
		}

		this.curriculumDao.insert(entity);
	}
	
	public void update(final String candidateUid, final CurriculumDto curriculum) {
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		final Curriculum oldCurriculum = candidate.getCurriculum();
		this.curriculumDao.delete(oldCurriculum);
		this.insert(candidateUid, curriculum);
	}

	public List<CurriculumTemplate> findCurriculumTemplates() {
		logger.debug("Find curriculum templates");
		return this.curriculumDao.findCurriculumTemplates();
	}

	public Curriculum findCandidatesCurriculum(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find curriculum of candidate {}", candidateUid);
		return this.curriculumDao.findByCandidateUid(candidateUid);
	}

	public Curriculum findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find curriculum by uid {}", uid);
		return this.curriculumDao.findByUid(uid);
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name, "Skill name can't be null");
		logger.debug("Find skill by name {}", name);
		return this.curriculumDao.findSkill(name);
	}

	public File export(String curriculumUid, CurriculumTemplate template) {
		Objects.requireNonNull(curriculumUid);
		Objects.requireNonNull(template);
		logger.debug("Export curriculum {} with template", curriculumUid, template);
		final Curriculum curriculum = this.curriculumDao.findByUid(curriculumUid);
		return exporter.export(curriculum, template);
	}

	protected CurriculumDao getDao() {
		return this.curriculumDao;
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}
}