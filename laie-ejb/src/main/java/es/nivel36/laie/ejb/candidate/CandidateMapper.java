package es.nivel36.laie.ejb.candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.core.model.AddressMapper;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureMapper;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.SimpleUserMapper;
import es.nivel36.laie.ejb.user.User;

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
		final File picture = entity.getPicture();
		if (picture != null) {
			dto.setAvatarUrl(picture.getPhysicalFile().getRelativePath());
		}
		dto.setInfojobsProfileUrl(entity.getInfojobsProfileUrl());
		dto.setJobProfile(entity.getJobProfile());
		dto.setLinkedinProfileUrl(entity.getLinkedinProfileUrl());
		dto.setName(entity.getName());
		dto.setOrigin(entity.getOrigin());
		final SimpleUserDto owner = mapOwner(entity.getOwner());
		dto.setOwner(owner);
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setRating(entity.getRating());
		dto.setSalary(entity.getSalary());
		dto.setSkype(entity.getSkype());
		dto.setSurname(entity.getSurname());
		final List<JobCandidatureDto> jobCandidaturesDto = mapJobCandidatures(entity);
		dto.setJobCandidature(jobCandidaturesDto);
		dto.setUid(entity.getUid());
		dto.setTags(mapTags(entity.getTags()));
		return dto;
	}

	private SimpleUserDto mapOwner(final User owner) {
		final SimpleUserMapper mapper = new SimpleUserMapper();
		return mapper.map(owner);
	}

	private Set<String> mapTags(Set<Tag> entities) {
		return entities.stream().map(Tag::getLabel).collect(Collectors.toSet());
	}

	private List<JobCandidatureDto> mapJobCandidatures(final Candidate entity) {
		final List<JobCandidature> jobCandidatures = entity.getJobCandidatures();
		if (jobCandidatures == null) {
			return new ArrayList<>();
		}
		final List<JobCandidatureDto> jobCandidaturesDto = new ArrayList<>(jobCandidatures.size());
		final JobCandidatureMapper jobCandidatureMapper = new JobCandidatureMapper();
		for (final JobCandidature jobCandidature : jobCandidatures) {
			final JobCandidatureDto jobCandidatureDto = jobCandidatureMapper.map(jobCandidature);
			jobCandidaturesDto.add(jobCandidatureDto);
		}
		return jobCandidaturesDto;
	}
}