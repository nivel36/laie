package es.nivel36.laie.ejb.curriculum.language;

import java.util.Objects;

import es.nivel36.core.model.Merger;

public class LenguageMerger implements Merger<Language, LanguageDto> {

	@Override
	public void merge(Language entity, LanguageDto dto) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(dto);
		entity.setName(dto.getName());
		entity.setLevel(dto.getLevel());
	}
}
