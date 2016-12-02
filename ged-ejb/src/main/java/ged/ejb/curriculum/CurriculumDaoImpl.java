package ged.ejb.curriculum;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class CurriculumDaoImpl extends AbstractDao<Long, Curriculum> implements CurriculumDao {

	@Inject
	public CurriculumDaoImpl(final EntityManager entityManger) {
		super(entityManger);
	}

	@Override
	public List<LanguageLevel> findAllLanguageLevels() {
		return findAll(LanguageLevel.class);
	}

	@Override
	public List<SkillLevel> findAllSkillLevels() {
		return findAll(SkillLevel.class);
	}

	@Override
	public Class<Curriculum> getType() {
		return Curriculum.class;
	}
}