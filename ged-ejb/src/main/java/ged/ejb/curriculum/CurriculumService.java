package ged.ejb.curriculum;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private CurriculumExporter exporter;

	public List<CurriculumTemplate> findCurriculumTemplates() {
		logger.debug("Find curriculum templates");
		return this.curriculumDao.findCurriculumTemplates();
	}

	public Curriculum findByCandidateUid(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find curriculum by candidate uid  {}", candidateUid);
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

	@Override
	protected AbstractDao<Curriculum> getDao() {
		return this.curriculumDao;
	}

	public void setCurriculumDao(final CurriculumDao curriculumDao) {
		Objects.requireNonNull(curriculumDao);
		this.curriculumDao = curriculumDao;
	}
}