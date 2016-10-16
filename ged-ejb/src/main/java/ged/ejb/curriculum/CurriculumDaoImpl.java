package ged.ejb.curriculum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDaoImpl extends AbstractDao<Long, Curriculum> implements CurriculumDao {

	private final Logger logger = Logger.getLogger(CurriculumDaoImpl.class.getName());

	@Inject
	public CurriculumDaoImpl(@Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
	}

	@Override
	public void deleteCurriculum(final Curriculum curriculum) {
		this.persistenceFacade.delete(curriculum);
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return this.persistenceFacade.findAll(LanguageLevel.class);
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return this.persistenceFacade.findAll(SkillLevel.class);
	}

	@Override
	public Curriculum findByCandidateId(final Long id) {
		Curriculum curriculum = null;
		try {
			final Map<String, Object> params = new HashMap<>();
			params.put("candidateId", id);
			curriculum = this.persistenceFacade.findByTypedQuery(Curriculum.class, "Curriculum.findByCandidateId",
					params);
		} catch (final NoResultException ex) {
			this.logger.log(Level.FINE, "User has no curriculum", ex);
			curriculum = null;
		}
		return curriculum;
	}

	@Override
	public Class<Curriculum> getClazz() {
		return Curriculum.class;
	}

	@Override
	public void insertCurriculum(final Curriculum curriculum) {
		this.persistenceFacade.insert(curriculum);
	}

	@Override
	public Curriculum updateCurriculum(final Curriculum curriculum) {
		return this.persistenceFacade.update(curriculum);
	}

}
