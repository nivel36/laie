package ged.ejb.curriculum;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDaoImpl extends AbstractDao<Long, Curriculum> implements CurriculumDao {

	private final Logger logger = Logger.getLogger(CurriculumDaoImpl.class.getName());

	@Inject
	public CurriculumDaoImpl(final EntityManager entityManger) {
		super(entityManger);
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return createQueryFromCriteria(LanguageLevel.class).getResultList();
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return createQueryFromCriteria(SkillLevel.class).getResultList();
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}
}
