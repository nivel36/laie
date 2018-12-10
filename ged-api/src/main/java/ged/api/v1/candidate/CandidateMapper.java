package ged.api.v1.candidate;

import java.util.Objects;

import javax.inject.Inject;

import ged.api.v1.mapper.AbstractMapper;
import ged.api.v1.mapper.Mapper;
import ged.ejb.candidate.Candidate;
import ged.ejb.core.Address;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Mapper
public class CandidateMapper implements AbstractMapper<Candidate, CandidateDto> {

	private final UserService userService;

	@Inject
	public CandidateMapper(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public Candidate mapDto(final CandidateDto candidateDto) {
		if (candidateDto == null) {
			return null;
		}
		final Candidate candidate = new Candidate();
		final User owner = this.userService.findUserByEmail(candidateDto.getOwnerEmail());
		candidate.setOwner(owner);
		final Address address = new Address();
		address.setCity(candidateDto.getCity());
		address.setState(candidateDto.getState());
		candidate.setAddress(address);
		candidate.setBornDate(candidateDto.getBornDate());
		candidate.setEmail(candidateDto.getEmail());
		candidate.setExpectedSalary(candidateDto.getExpectedSalary());
		candidate.setImageFileName(candidateDto.getImageFileName());
		candidate.setInfojobsProfileUrl(candidateDto.getInfojobsProfileUrl());
		candidate.setJobProfile(candidateDto.getJobProfile());
		candidate.setLinkedinProfileUrl(candidateDto.getLinkedinProfileUrl());
		candidate.setName(candidateDto.getName());
		candidate.setPhoneNumber(candidateDto.getPhoneNumber());
		candidate.setRating(candidateDto.getRating());
		candidate.setSalary(candidateDto.getSalary());
		candidate.setSkype(candidateDto.getSkype());
		candidate.setSurname(candidateDto.getSurname());
		return candidate;
	}

	@Override
	public CandidateDto mapEntity(final Candidate candidate) {
		if (candidate == null) {
			return null;
		}
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
		candidateDto.setSurname(candidate.getSurname());
		return candidateDto;
	}
}
