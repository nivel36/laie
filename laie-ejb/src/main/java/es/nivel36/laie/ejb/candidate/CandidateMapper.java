package es.nivel36.laie.ejb.candidate;

import java.util.ArrayList;
import java.util.List;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureMapper;

public class CandidateMapper implements Mapper<Candidate, CandidateDto> {

	@Override
	public CandidateDto map(final Candidate entity) {
		if (entity == null) {
			return null;
		}
		final CandidateDto dto = new CandidateDto();
		final Address address = entity.getAddress();
		final AddressDto addressDto = new AddressMapper().map(address);
		dto.setAddress(addressDto);
		dto.setBornDate(entity.getBornDate());
		dto.setEmail(entity.getEmail());
		dto.setExpectedSalary(entity.getExpectedSalary());
		dto.setPicturesPath(entity.getPicture().getPhysicalFile().getRelativePath());
		dto.setInfojobsProfileUrl(entity.getInfojobsProfileUrl());
		dto.setJobProfile(entity.getJobProfile());
		dto.setLinkedinProfileUrl(entity.getLinkedinProfileUrl());
		dto.setName(entity.getName());
		dto.setOrigin(entity.getOrigin());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setRating(entity.getRating());
		dto.setSalary(entity.getSalary());
		dto.setSkype(entity.getSkype());
		dto.setSurname(entity.getSurname());
		final List<JobCandidatureDto> jobCandidaturesDto = mapJobCandidatures(entity, dto);
		dto.setJobCandidature(jobCandidaturesDto);
		return dto;
	}

	private List<JobCandidatureDto> mapJobCandidatures(final Candidate entity, final CandidateDto dto) {
		final List<JobCandidature> jobCandidatures = entity.getJobCandidatures();
		if (jobCandidatures == null) {
			return null;
		}
		final List<JobCandidatureDto> jobCandidaturesDto = new ArrayList<JobCandidatureDto>(jobCandidatures.size());
		final JobCandidatureMapper jobCandidatureMapper = new JobCandidatureMapper();
		for (final JobCandidature jobCandidature : jobCandidatures) {
			final JobCandidatureDto jobCandidatureDto = jobCandidatureMapper.map(jobCandidature);
			jobCandidaturesDto.add(jobCandidatureDto);
		}
		return jobCandidaturesDto;
	}
}