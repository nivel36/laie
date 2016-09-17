package ged.ejb.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 2907479775923990369L;

	@Column(length = 64)
	private String city;

	@Column(length = 64)
	private String country;

	@Column(length = 16)
	private String door;

	@Column(length = 16)
	private String number;

	@Column(length = 64)
	private String state;

	@Column(length = 16)
	private String storey;

	@Column(length = 128)
	private String street;

	@Column(length = 5)
	private String zipCode;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Address other = (Address) obj;
		if (this.city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!this.city.equals(other.city)) {
			return false;
		}
		if (this.country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!this.country.equals(other.country)) {
			return false;
		}
		if (this.door == null) {
			if (other.door != null) {
				return false;
			}
		} else if (!this.door.equals(other.door)) {
			return false;
		}
		if (this.number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!this.number.equals(other.number)) {
			return false;
		}
		if (this.state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!this.state.equals(other.state)) {
			return false;
		}
		if (this.storey == null) {
			if (other.storey != null) {
				return false;
			}
		} else if (!this.storey.equals(other.storey)) {
			return false;
		}
		if (this.street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!this.street.equals(other.street)) {
			return false;
		}
		if (this.zipCode == null) {
			if (other.zipCode != null) {
				return false;
			}
		} else if (!this.zipCode.equals(other.zipCode)) {
			return false;
		}
		return true;
	}

	public String getCity() {
		return this.city;
	}

	public String getCountry() {
		return this.country;
	}

	public String getDoor() {
		return this.door;
	}

	public String getNumber() {
		return this.number;
	}

	public String getState() {
		return this.state;
	}

	public String getStorey() {
		return this.storey;
	}

	public String getStreet() {
		return this.street;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.city == null) ? 0 : this.city.hashCode());
		result = (prime * result) + ((this.country == null) ? 0 : this.country.hashCode());
		result = (prime * result) + ((this.door == null) ? 0 : this.door.hashCode());
		result = (prime * result) + ((this.number == null) ? 0 : this.number.hashCode());
		result = (prime * result) + ((this.state == null) ? 0 : this.state.hashCode());
		result = (prime * result) + ((this.storey == null) ? 0 : this.storey.hashCode());
		result = (prime * result) + ((this.street == null) ? 0 : this.street.hashCode());
		result = (prime * result) + ((this.zipCode == null) ? 0 : this.zipCode.hashCode());
		return result;
	}

	private boolean isNotEmpty(final String string) {
		return (string != null) && !"".equals(string.trim());
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public void setDoor(final String door) {
		this.door = door;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public void setStorey(final String storey) {
		this.storey = storey;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		final String eol = System.getProperty("line.separator");
		final StringBuilder sb = new StringBuilder();
		if (isNotEmpty(this.street)) {
			sb.append(this.street);
			sb.append(" ");
		}
		if (isNotEmpty(this.number)) {
			sb.append(this.number);
			sb.append(", ");
		}
		if (isNotEmpty(this.storey)) {
			sb.append(this.storey);
			sb.append(", ");
		}
		if (isNotEmpty(this.door)) {
			sb.append(this.door);
			sb.append(eol);
		}
		if (isNotEmpty(this.zipCode)) {
			sb.append(this.zipCode);
			sb.append(eol);
		}
		if (isNotEmpty(this.city)) {
			sb.append(this.city);
			sb.append(eol);
		}
		if (isNotEmpty(this.state)) {
			sb.append(this.state);
			sb.append(eol);
		}
		if (isNotEmpty(this.country)) {
			sb.append(this.country);
		}
		return sb.toString();
	}
}
