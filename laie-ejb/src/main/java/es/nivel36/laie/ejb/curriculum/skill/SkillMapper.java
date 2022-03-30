package es.nivel36.laie.ejb.curriculum.skill;

import es.nivel36.core.model.Mapper;

public class SkillMapper implements Mapper<Skill, String> {

	@Override
	public String map(final Skill entity) {
		if (entity == null) {
			return null;
		}
		return entity.getName();
	}
}