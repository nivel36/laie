package es.nivel36.laie.ejb.job.offer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.user.SimpleUserDto;

public class JobOfferDto implements Serializable {

	private static final long serialVersionUID = -7963973908753316393L;

	private AddressDto address;

	private LocalDate dateClosed;

	private LocalDate dateOpened;

	private String description;

	private Set<JobCandidature> jobCandidatures;

	private SimpleUserDto owner;

	private Integer places = 1;

	private boolean published;

	private Set<SimpleUserDto> recruiters;

	private Integer salary;

	private JobOfferState state;

	private String title;

	private String uid;

	public AddressDto getAddress() {
		return this.address;
	}

	public LocalDate getDateClosed() {
		return this.dateClosed;
	}

	public LocalDate getDateOpened() {
		return this.dateOpened;
	}

	public String getDescription() {
		return this.description;
	}

	public Set<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public SimpleUserDto getOwner() {
		return this.owner;
	}

	public Integer getPlaces() {
		return this.places;
	}

	public Set<SimpleUserDto> getRecruiters() {
		return this.recruiters;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public JobOfferState getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUid() {
		return uid;
	}

	public boolean hasCandidatureOf(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.jobCandidatures.isEmpty()) {
			return false;
		}
		for (final JobCandidature jobCandidature : this.jobCandidatures) {
			if (jobCandidature.getCandidate().equals(candidate)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasState(final JobOfferState state) {
		if (this.state == null) {
			return state == null;
		} else {
			return this.state.equals(state);
		}
	}

	public boolean isOpen() {
		return this.hasState(JobOfferState.OPENED);
	}

	public boolean isPublished() {
		return this.published;
	}

	public void setAddress(final AddressDto address) {
		this.address = address;
	}

	public void setDateClosed(final LocalDate dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setDateOpened(final LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	void setJobCandidatures(final Set<JobCandidature> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	void setOwner(final SimpleUserDto owner) {
		this.owner = owner;
	}

	public void setPlaces(final Integer places) {
		this.places = places;
	}

	void setPublished(final boolean published) {
		this.published = published;
	}

	void setRecruiters(final Set<SimpleUserDto> recruiters) {
		this.recruiters = recruiters;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	void setState(final JobOfferState state) {
		this.state = state;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final JobOfferDto other = (JobOfferDto) obj;
		return Objects.equals(this.dateOpened, other.dateOpened) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.places, other.places);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dateOpened, this.title, this.places);
	}

	@Override
	public String toString() {
		return this.title;
	}
}
