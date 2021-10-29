package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.core.Mapper;

public class SimpleJobOfferMapper implements Mapper<JobOffer, SimpleJobOfferDto> {

	@Override
	public SimpleJobOfferDto map(final JobOffer entity) {
		final SimpleJobOfferDto dto = new SimpleJobOfferDto();
		dto.setState(entity.getState());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		return dto;
	}
}
