package es.nivel36.laie.ejb.client;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "CONTACT", indexes = { @Index(name = "UX_CONTACT_EMAIL", columnList = "EMAIL", unique = true) }, //
		uniqueConstraints = { @UniqueConstraint(name = "UQ_CONTACT_EMAIL", columnNames = { "EMAIL" }) })
public class Contact extends AbstractEntity implements Subject, Auditable {

	private static final long serialVersionUID = -2403549918176092142L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	private Client client;

	@Email
	@NotBlank
	@Column(name = "EMAIL", length = 128, nullable = false, unique = true)
	@FullTextField(name = "_email")
	@KeywordField(name = "email", sortable = Sortable.YES)
	protected String email;

	@Column(name = "LANGUAGE", length = 2)
	private String language;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<>();

	@NotBlank
	@Column(name = "NAME", nullable = false, length = 128)
	@FullTextField(name = "_name")
	@KeywordField(name = "name", sortable = Sortable.YES)
	protected String name;

	@Column(name = "PHONE_NUMBER", length = 12)
	protected String phoneNumber;

	@Column(name = "POSITION", length = 128)
	private String position;

	@NotBlank
	@Column(name = "SURNAME", nullable = false, length = 128)
	@FullTextField(name = "_surname")
	@KeywordField(name = "surname", sortable = Sortable.YES)
	protected String surname;

	public void addMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.add(meeting);
	}

	public void removeMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.meetings.remove(meeting);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final Contact other = (Contact) obj;
		return Objects.equals(email, other.email);
	}

	public Client getClient() {
		return this.client;
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

	public String getLanguage() {
		return this.language;
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

	public String getPosition() {
		return this.position;
	}

	@Override
	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = prime * Objects.hash(email);
		return result;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLanguage(final String language) {
		this.language = language;
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

	public void setPosition(final String position) {
		this.position = position;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}

	@Override
	public String getEntityName() {
		return "CONTACT";
	}

	@Override
	public String getEntityTitle() {
		return this.getFullName();
	}
}