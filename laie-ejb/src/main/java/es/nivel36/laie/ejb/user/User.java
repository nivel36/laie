package es.nivel36.laie.ejb.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.Rating;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.submission.JobSubmissionEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "PERSON", indexes = {
		@Index(name = "UX_PERSON_EMAIL", columnList = "EMAIL", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = "UQ_PERSON_EMAIL", columnNames = { "EMAIL" }) })
public class User extends AbstractEntity implements Subject {

	private static final long serialVersionUID = -3719561601581901723L;
	
    @NotBlank
    @Column(name = "NAME", nullable = false, length = 128)
    @FullTextField(name = "_name")
    @KeywordField(sortable = Sortable.YES)
    private String name;
    
    @NotBlank
    @Column(name = "SURNAME", nullable = false, length = 128)
    @FullTextField(name = "_surname")
    @KeywordField(sortable = Sortable.YES)
    private String surname;

	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_BOOKMARK",
               joinColumns = @JoinColumn(name = "PERSON_ID"),
               inverseJoinColumns = @JoinColumn(name = "BOOKMARK_ID"))
    private Set<Bookmark> bookmarks = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Candidate> candidates = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Client> clients = new HashSet<>();

    @Column(name = "DATE_OF_JOIN")
    @GenericField(sortable = Sortable.YES)
    private LocalDate dateOfJoin;

    @NotBlank
    @Column(name = "EMAIL", length = 128, nullable = false)
    @FullTextField(name = "_email")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<JobSubmissionEvent> jobSubmissionEvents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private Set<JobOffer> jobOffers = new HashSet<>();

    @NotBlank
    @Column(name = "LANGUAGE", nullable = false)
    private String language;

    @GenericField(sortable = Sortable.YES)
    @Column(name = "LAST_CONNECTION")
    private LocalDateTime lastConnection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MANAGER_ID")
    @IndexedEmbedded(includeDepth = 1)
    private User manager;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_MEETING",
               joinColumns = @JoinColumn(name = "PERSON_ID"),
               inverseJoinColumns = @JoinColumn(name = "MEETING_ID"))
    private Set<Meeting> meetings = new HashSet<>();

    @Column(name = "PHONE_NUMBER", length = 12)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "PICTURE_ID")
    private PhysicalFile picture;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "JOB_PERSON",
               joinColumns = @JoinColumn(name = "PERSON_ID"),
               inverseJoinColumns = @JoinColumn(name = "JOB_ID"))
    private Set<JobOffer> recruiterOfJobOffers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 16)
    private Role role;

    @NotNull
    @Column(name = "ROWS_PER_PAGE", nullable = false)
    private Integer rowsPerPage = 10;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private Set<User> team = new HashSet<>();

	public void addMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.add(meeting);
	}

	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public LocalDate getDateOfJoin() {
		return this.dateOfJoin;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getFullName() {
		if (this.name == null) {
			return null;
		}
		return new StringBuilder(this.name).append(" ").append(this.surname).toString();
	}

	public Set<JobSubmissionEvent> getJobSubmissionEvents() {
		return jobSubmissionEvents;
	}

	public Set<JobOffer> getJobOffers() {
		return jobOffers;
	}

	public String getLanguage() {
		return this.language;
	}

	public LocalDateTime getLastConnection() {
		return this.lastConnection;
	}

	public User getManager() {
		return this.manager;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public PhysicalFile getPicture() {
		return this.picture;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public Set<JobOffer> getRecruiterOfJobOffers() {
		return recruiterOfJobOffers;
	}

	public Role getRole() {
		return role;
	}

	public Integer getRowsPerPage() {
		return this.rowsPerPage;
	}

	@Override
	public String getSurname() {
		return this.surname;
	}

	public Set<User> getTeam() {
		return team;
	}

	public boolean isManaged() {
		return this.manager != null;
	}

	public void removeMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.remove(meeting);
	}

	public void setBookmarks(final Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setCandidates(final Set<Candidate> candidates) {
		this.candidates = candidates;
	}

	public void setClients(final Set<Client> clients) {
		this.clients = clients;
	}

	public void setDateOfJoin(final LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setJobSubmissionEvents(final Set<JobSubmissionEvent> jobSubmissionEvents) {
		this.jobSubmissionEvents = jobSubmissionEvents;
	}

	public void setJobOffers(final Set<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastConnection(final LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setMeetings(final Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPicture(final PhysicalFile picture) {
		this.picture = picture;
	}

	public void setRatings(final Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public void setRecruiterOfJobOffers(final Set<JobOffer> recruiterOfJobOffers) {
		this.recruiterOfJobOffers = recruiterOfJobOffers;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setRowsPerPage(final Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setTeam(final Set<User> team) {
		this.team = team;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		return Objects.equals(other.email, this.email);
	}

	@Override
	public int hashCode() {
		return 31 * Objects.hash(this.email);
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}
