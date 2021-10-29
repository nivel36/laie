package es.nivel36.laie.ejb.candidate;

import java.io.Serializable;
import java.util.Objects;

public class SimpleCandidateDto implements Serializable {

	private static final long serialVersionUID = 3753490220364619995L;

	private String candidateUid;

	private String email;

	private String fullName;

	private String photo;

	public String getCandidateUid() {
		return candidateUid;
	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoto() {
		return photo;
	}

	void setCandidateUid(String candidateUid) {
		this.candidateUid = candidateUid;
	}

	void setEmail(String email) {
		this.email = email;
	}

	void setFullName(String fullName) {
		this.fullName = fullName;
	}

	void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleCandidateDto other = (SimpleCandidateDto) obj;
		return Objects.equals(email, other.email);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return fullName;
	}
}
