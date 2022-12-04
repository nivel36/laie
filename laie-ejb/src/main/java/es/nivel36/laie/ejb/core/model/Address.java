package es.nivel36.laie.ejb.core.model;

import java.io.Serializable;
import java.util.Objects;

import org.bouncycastle.util.Store;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.SortableField;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Table(name="ADDRESS")
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = -8221733995443213238L;

	@FullTextField(name = "_city")
	@FullTextField(name = "city", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "city")
	@Column(name="CITY", length = 128)
	private String city;

	@Column(name="COUNTRY", length = 128)
	private String country;

	@Column(name="DOOR", length = 16)
	private String door;

	@Column(name="NUMBER", length = 16)
	private String number;

	@FullTextField(name = "_region")
	@FullTextField(name = "region", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "region")
	@Column(name="REGION", length = 128)
	private String region;

	@Column(name="STOREY", length = 16)
	private String storey;

	@Column(name="STREET", length = 128)
	private String street;

	@Column(name="ZIP_CODE", length = 8)
	private String zipCode;

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
		final Address other = (Address) obj;
		return Objects.equals(this.city, other.city) && Objects.equals(this.country, other.country)
				&& Objects.equals(this.door, other.door) && Objects.equals(this.number, other.number)
				&& Objects.equals(this.region, other.region) && Objects.equals(this.storey, other.storey)
				&& Objects.equals(this.street, other.street) && Objects.equals(this.zipCode, other.zipCode);
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

	public String getRegion() {
		return this.region;
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
		return Objects.hash(this.city, this.country, this.door, this.number, this.region, this.storey, this.street,
				this.zipCode);
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

	public void setRegion(final String region) {
		this.region = region;
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
		if (this.isNotEmpty(this.street)) {
			sb.append(this.street);
			sb.append(" ");
		}
		if (this.isNotEmpty(this.number)) {
			sb.append(this.number);
			sb.append(", ");
		}
		if (this.isNotEmpty(this.storey)) {
			sb.append(this.storey);
			sb.append(", ");
		}
		if (this.isNotEmpty(this.door)) {
			sb.append(this.door);
			sb.append(eol);
		}
		if (this.isNotEmpty(this.zipCode)) {
			sb.append(this.zipCode);
			sb.append(eol);
		}
		if (this.isNotEmpty(this.city)) {
			sb.append(this.city);
			sb.append(eol);
		}
		if (this.isNotEmpty(this.region)) {
			sb.append(this.region);
			sb.append(eol);
		}
		if (this.isNotEmpty(this.country)) {
			sb.append(this.country);
		}
		return sb.toString();
	}
}
