package ged.web.view.dto;

public class CandidateCardDto {

	private String fullName;

	private long id;

	private String personalProfile;

	public String getFullName() {
		return fullName;
	}

	public long getId() {
		return id;
	}

	public String getPersonalProfile() {
		return personalProfile;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPersonalProfile(String personalProfile) {
		this.personalProfile = personalProfile;
	}

}
