package es.nivel36.laie.ejb.core.bookmark;

import es.nivel36.core.model.Mapper;

public class BookmarkMapper implements Mapper<Bookmark, BookmarkDto> {

	@Override
	public BookmarkDto map(final Bookmark entity) {
		if(entity == null) {
			return null;
		}
		final BookmarkDto dto = new BookmarkDto();
		dto.setImageUrl(entity.imageUrl);
		dto.setTitle(entity.title);
		dto.setUrl(entity.url);
		return dto;
	}
}
