package ged.api.v1.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ged.api.v1.Dto;

public class UserDto implements Dto {

	private LocalDate dateOfJoin;

	@NotNull
	private String email;

	private String imageFileName;

	@NotNull
	private String language;

	private LocalDateTime lastConnection;

	private String managerEmail;

	@NotNull
	private String name;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	private String phoneNumber;

	@NotNull
	private String roleName;

	@NotNull
	private String surname;

	
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

	public LocalDate getDateOfJoin() {
		return this.dateOfJoin;
	}

	public String getEmail() {
		return this.email;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public String getLanguage() {
		return this.language;
	}

	public LocalDateTime getLastConnection() {
		return this.lastConnection;
	}

	public String getManagerEmail() {
		return this.managerEmail;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (this.email == null ? 0 : this.email.hashCode());
		return result;
	}

	public void setDateOfJoin(final LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastConnection(final LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public void setManagerEmail(final String managerEmail) {
		this.managerEmail = managerEmail;
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
}
