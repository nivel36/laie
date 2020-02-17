package ged.ejb.curriculum;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.export.CurriculumExporter;

@Stateless
public class CurriculumService extends AbstractService<Curriculum> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private CurriculumDao curriculumDao;

	@Inject
	@Repository
	private EducationDao educationDao;
	
	@Inject
	private CurriculumExporter exporter;

	@Inject
	@Repository
	private JobExperienceDao jobExperienceDao;

	@Inject
	@Repository
	private SkillDao skillDao;
	
	public List<CurriculumTemplate> findCurriculumTemplates() {
		logger.debug("Find curriculum templates");
		return this.curriculumDao.findCurriculumTemplates();
	}

	public List<SkillLevel> findSkillLevels() {
		logger.debug("Find skill levels");
		return this.skillDao.findSkillLevels();
	}

	public Curriculum findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find curriculum by candidate  {}", candidate);
		return this.curriculumDao.findByCandidate(candidate);
	}

	public Curriculum findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find curriculum by uid {}", uid);
		return this.curriculumDao.findByUid(uid);
	}

	public Education findEducation(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find education by uid {}", uid);
		return this.educationDao.findByUid(uid);
	}

	public JobExperience findJobExperience(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find jobExperience by uid {}", uid);
		return this.jobExperienceDao.findByUid(uid);
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name, "Skill name can't be null");
		logger.debug("Find skill by name {}", name);
		return this.skillDao.findSkill(name);
	}
	
	public File export(Curriculum curriculum, CurriculumTemplate template) {
		return exporter.export(curriculum, template);
	}

	@Override
	protected AbstractDao<Curriculum> getDao() {
		return this.curriculumDao;
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}

	public void setEducationDao(final EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	public void setJobExperienceDao(final JobExperienceDao jobExperienceDao) {
		this.jobExperienceDao = jobExperienceDao;
	}
}