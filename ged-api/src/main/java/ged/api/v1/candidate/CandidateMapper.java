package ged.api.v1.candidate;

import java.util.Objects;

import ged.api.v1.mapper.AbstractMapper;
import ged.ejb.candidate.Candidate;

public class CandidateMapper extends AbstractMapper<Candidate, CandidateDto> {

	@Override
	public Candidate mapDto(final CandidateDto dtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidateDto mapEntity(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(candidate.getAddress());
		Objects.requireNonNull(candidate.getOwner());
		final CandidateDto candidateDto = new CandidateDto();
		candidateDto.setBornDate(candidate.getBornDate());
		candidateDto.setCity(candidate.getAddress().getCity());
		candidateDto.setEmail(candidate.getEmail());
		candidateDto.setExpectedSalary(candidate.getExpectedSalary());
		candidateDto.setId(candidate.getId());
		candidateDto.setImageFileName(candidate.getImageFileName());
		candidateDto.setInfojobsProfileUrl(candidate.getInfojobsProfileUrl());
		candidateDto.setJobProfile(candidate.getJobProfile());
		candidateDto.setLinkedinProfileUrl(candidate.getLinkedinProfileUrl());
		candidateDto.setName(candidate.getName());
		candidateDto.setOwnerEmail(candidate.getOwner().getEmail());
		candidateDto.setPhoneNumber(candidate.getPhoneNumber());
		candidateDto.setRating(candidate.getRating());
		candidateDto.setSalary(candidate.getSalary());
		candidateDto.setSkype(candidate.getSkype());
		candidateDto.setState(candidate.getAddress().getState());
		candidateDto.setSurename(candidate.getSurename());
		return candidateDto;
	}

}
