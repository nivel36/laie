package ged.ejb.service.candidate;

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

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getDoor() {
		return door;
	}

	public String getNumber() {
		return number;
	}

	public String getState() {
		return state;
	}

	public String getStorey() {
		return storey;
	}

	public String getStreet() {
		return street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStorey(String storey) {
		this.storey = storey;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((door == null) ? 0 : door.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((storey == null) ? 0 : storey.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (door == null) {
			if (other.door != null)
				return false;
		} else if (!door.equals(other.door))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (storey == null) {
			if (other.storey != null)
				return false;
		} else if (!storey.equals(other.storey))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String eol = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		if (street != null && !street.trim().equals("")) {
			sb.append(street);
			sb.append(" ");
		}
		if (number != null && !number.trim().equals("")) {
			sb.append(number);
			sb.append(", ");
		}
		if (storey != null && !storey.trim().equals("")) {
			sb.append(storey);
			sb.append(", ");
		}
		if (door != null && !door.trim().equals("")) {
			sb.append(door);
			sb.append(eol);
		}
		if (zipCode != null && !zipCode.trim().equals("")) {
			sb.append(zipCode);
			sb.append(eol);
		}
		if (city != null && !city.trim().equals("")) {
			sb.append(city);
			sb.append(eol);
		}
		if (state != null && !state.trim().equals("")) {
			sb.append(state);
			sb.append(eol);
		}
		if (country != null && !country.trim().equals("")) {
			sb.append(country);
		}
		return sb.toString();
	}
}
