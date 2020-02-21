package ged.ejb.curriculum;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class SkillDao extends AbstractIndexedDao<Skill> {

	public List<SkillLevel> findSkillLevels() {
		return this.findAll(SkillLevel.class, Page.ALL_RESULTS);
	}

	public Skill findSkill(final String name) {
		Objects.requireNonNull(name);
		return this.findByQuery(Skill.class, "Skill.findByName", map("name", name));
	}

	@Override
	protected Class<Skill> getType() {
		return Skill.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "name" };
	}

}
