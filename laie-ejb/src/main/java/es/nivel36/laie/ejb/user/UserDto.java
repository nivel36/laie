package es.nivel36.laie.ejb.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -5006849994381781200L;

	private LocalDate dateOfJoin;

	@Email
	@NotNull
	private String email;

	private String avatarUrl;

	@NotNull
	private String language;

	private LocalDateTime lastConnection;

	private SimpleUserDto manager;

	@NotNull
	private String name;

	private String phoneNumber;

	@NotNull
	private String roleName;

	@NotNull
	private String surname;

	private String uid;
	
	public String getFullName() {
		return toString();
	}

	public LocalDate getDateOfJoin() {
		return this.dateOfJoin;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public String getLanguage() {
		return this.language;
	}

	public LocalDateTime getLastConnection() {
		return this.lastConnection;
	}

	public SimpleUserDto getManager() {
		return this.manager;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getUid() {
		return uid;
	}

	public void setDateOfJoin(final LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setAvatarUrl(final String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastConnection(final LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public void setManager(final SimpleUserDto manager) {
		this.manager = manager;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	void setUid(final String uid) {
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
		final UserDto other = (UserDto) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (this.email == null ? 0 : this.email.hashCode());
		return result;
	}

	
	@Override
	public String toString() {
		return name + " " + surname;
	}
}
