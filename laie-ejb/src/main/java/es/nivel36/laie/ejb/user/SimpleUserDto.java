package es.nivel36.laie.ejb.user;

import java.io.Serializable;
import java.util.Objects;

public class SimpleUserDto implements Serializable {

	private static final long serialVersionUID = -4948268032591770106L;

	private String email;

	private String fullName;

	private String avatarUrl;

	private String uid;

	public SimpleUserDto() {
	}

	public SimpleUserDto(final UserDto userDto) {
		this.email = userDto.getEmail();
		this.fullName = userDto.getFullName();
		this.avatarUrl = userDto.getAvatarUrl();
		this.uid = userDto.getUid();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final SimpleUserDto other = (SimpleUserDto) obj;
		return Objects.equals(this.email, other.email);
	}

	public String getEmail() {
		return this.email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	void setEmail(final String email) {
		this.email = email;
	}

	void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	void setAvatarUrl(final String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.fullName;
	}
}
