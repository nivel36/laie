package es.nivel36.laie.ejb.job.offer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import es.nivel36.laie.ejb.client.SimpleClientDto;
import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.user.SimpleUserDto;

public class JobOfferDto implements Serializable {

	private static final long serialVersionUID = -7028791048538799564L;

	private AddressDto address;

	private SimpleClientDto client;

	private LocalDate closeDate;

	private String description;

	private Set<JobCandidatureDto> jobCandidatures = new HashSet<>();

	private LocalDate openDate;

	private SimpleUserDto owner;

	private Integer places = 1;

	private boolean published;

	private Set<SimpleUserDto> recruiters = new HashSet<>();

	private Integer salary;

	private JobOfferState state;

	private String title;

	private String uid;

	public AddressDto getAddress() {
		if (this.address == null) {
			address = new AddressDto();
		}
		return this.address;
	}

	public final SimpleClientDto getClient() {
		return client;
	}

	public LocalDate getCloseDate() {
		return this.closeDate;
	}

	public String getDescription() {
		return this.description;
	}

	public Set<JobCandidatureDto> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public LocalDate getOpenDate() {
		return this.openDate;
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

	public final void setClient(SimpleClientDto client) {
		this.client = client;
	}

	public void setCloseDate(final LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	void setJobCandidatures(final Set<JobCandidatureDto> jobCandidatures) {
		this.jobCandidatures = jobCandidatures;
	}

	public void setOpenDate(final LocalDate openDate) {
		this.openDate = openDate;
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
		return Objects.equals(this.openDate, other.openDate) && Objects.equals(this.title, other.title)
				&& Objects.equals(this.places, other.places);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.openDate, this.title, this.places);
	}

	@Override
	public String toString() {
		return this.title;
	}
}
