package es.nivel36.laie.ejb.curriculum.language;

import es.nivel36.core.model.Mapper;

public class LanguageMapper implements Mapper<Language, LanguageDto> {

	@Override
	public LanguageDto map(final Language entity) {
		if (entity == null) {
			return null;
		}
		final LanguageDto dto = new LanguageDto();
		dto.setLevel(entity.getLevel());
		dto.setName(entity.getName());
		return dto;
	}
}