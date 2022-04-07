package es.nivel36.laie.ejb.candidate;

import es.nivel36.laie.ejb.core.model.AddressMerger;
import es.nivel36.laie.ejb.core.model.Merger;

public class CandidateMerger implements Merger<Candidate, CandidateDto> {

	private final AddressMerger addressMerger;

	public CandidateMerger() {
		addressMerger = new AddressMerger();
	}

	@Override
	public void merge(final Candidate entity, final CandidateDto dto) {
		addressMerger.merge(entity.getAddress(), dto.getAddress());
		entity.setBornDate(dto.getBornDate());
		entity.setEmail(dto.getEmail());
		entity.setExpectedSalary(dto.getExpectedSalary());
		entity.setInfojobsProfileUrl(dto.getInfojobsProfileUrl());
		entity.setJobProfile(dto.getJobProfile());
		entity.setLinkedinProfileUrl(dto.getLinkedinProfileUrl());
		entity.setName(dto.getName());
		entity.setOrigin(dto.getOrigin());
		entity.setPhoneNumber(dto.getPhoneNumber());
		entity.setRating(dto.getRating());
		entity.setSalary(dto.getSalary());
		entity.setSkype(dto.getSkype());
		entity.setSurname(dto.getSurname());
		entity.setUid(dto.getUid());
	}
}
