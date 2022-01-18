package es.nivel36.laie.ejb.candidate;

import java.io.Serializable;
import java.util.Objects;

public class SimpleCandidateDto implements Serializable {

	private static final long serialVersionUID = 3753490220364619995L;

	private String uid;

	private String email;

	private String fullName;

	private String avatarUrl;
	
	private Integer rating;

	public String getUid() {
		return uid;
	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return fullName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public Integer getRating() {
		return rating;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

	void setEmail(String email) {
		this.email = email;
	}

	void setFullName(String fullName) {
		this.fullName = fullName;
	}

	void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	void setRating(Integer rating) {
		this.rating = rating;
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
