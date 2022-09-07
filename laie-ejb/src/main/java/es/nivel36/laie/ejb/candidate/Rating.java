package es.nivel36.laie.ejb.candidate;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UQ_Rating_Candidate_Rating_User", columnNames = { "candidate", "rating", "user" }) })
public class Rating extends AbstractEntity {

	private static final long serialVersionUID = -923213871691949551L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "candidate", nullable = false)
	private Candidate candidate;

	private String comment;

	@NotNull
	private Integer rating;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	public Candidate getCandidate() {
		return candidate;
	}

	public String getComment() {
		return comment;
	}

	public Integer getRating() {
		return rating;
	}

	public User getUser() {
		return user;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		return Objects.equals(candidate, other.candidate) && Objects.equals(rating, other.rating)
				&& Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(candidate, rating, user);
		return result;
	}

	@Override
	public String toString() {
		return user + ", candidate=" + candidate + ", rating=" + rating;
	}
}
