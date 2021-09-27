package es.nivel36.laie.api.v1.candidate;

import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.api.v1.mapper.AbstractMapper;
import es.nivel36.laie.api.v1.mapper.Mapper;
import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

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
		final User owner = this.userService.findByEmail(candidateDto.getOwnerEmail());
		candidate.setOwner(owner);
		final Address address = new Address();
		address.setCity(candidateDto.getCity());
		address.setRegion(candidateDto.getState());
		candidate.setAddress(address);
		candidate.setBornDate(candidateDto.getBornDate());
		candidate.setEmail(candidateDto.getEmail());
		candidate.setExpectedSalary(candidateDto.getExpectedSalary());
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
		candidateDto.setImageFileName(candidate.getPicture().getName());
		candidateDto.setInfojobsProfileUrl(candidate.getInfojobsProfileUrl());
		candidateDto.setJobProfile(candidate.getJobProfile());
		candidateDto.setLinkedinProfileUrl(candidate.getLinkedinProfileUrl());
		candidateDto.setName(candidate.getName());
		candidateDto.setOwnerEmail(candidate.getOwner().getEmail());
		candidateDto.setPhoneNumber(candidate.getPhoneNumber());
		candidateDto.setRating(candidate.getRating());
		candidateDto.setSalary(candidate.getSalary());
		candidateDto.setSkype(candidate.getSkype());
		candidateDto.setState(candidate.getAddress().getRegion());
		candidateDto.setSurname(candidate.getSurname());
		return candidateDto;
	}
}
