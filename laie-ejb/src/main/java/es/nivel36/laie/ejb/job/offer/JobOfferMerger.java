package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMerger;
import es.nivel36.laie.ejb.core.model.Merger;

public class JobOfferMerger implements Merger<JobOffer, JobOfferDto>{
	
	private final AddressMerger addressMerger;
	
	public JobOfferMerger() {
		this.addressMerger = new AddressMerger();
	}
	
	@Override
	public void merge(JobOffer entity, JobOfferDto dto) {
		entity.setTitle(dto.getTitle());
		entity.setState(dto.getState());
		entity.setOpenDate(dto.getOpenDate());
		entity.setDescription(dto.getDescription());
		entity.setPlaces(dto.getPlaces());
		entity.setPublished(dto.isPublished());
		entity.setSalary(dto.getSalary());
		final Address address = entity.getAddress();
		final AddressDto addressDto = dto.getAddress();
		addressMerger.merge(address, addressDto);
	}
}