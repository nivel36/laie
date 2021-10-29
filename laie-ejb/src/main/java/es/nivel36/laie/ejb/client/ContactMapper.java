package es.nivel36.laie.ejb.client;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;

public class ContactMapper implements Mapper<Contact, ContactDto> {

	@Override
	public ContactDto map(final Contact entity) {
		final ContactDto dto = new ContactDto();
		dto.setEmail(entity.getEmail());
		dto.setLanguage(entity.getLanguage());
		dto.setName(entity.getName());
		dto.setPhoneNumber(entity.getPhoneNumber());
		final File picture = entity.getPicture();
		if (picture != null) {
			dto.setPicturesUrl(picture.getPhysicalFile().getRelativePath());
		}
		dto.setPosition(entity.getPosition());
		dto.setSurname(entity.getSurname());
		return null;
	}
}
