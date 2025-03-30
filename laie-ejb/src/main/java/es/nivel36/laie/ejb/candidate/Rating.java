package es.nivel36.laie.ejb.candidate;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RATING", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_RATING_CANDIDATE_RATING_USER", columnNames = { "CANDIDATE_ID", "SCORE",
				"USER_ID" }) })
public class Rating extends AbstractEntity {

	private static final long serialVersionUID = -923213871691949551L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;

	@NotNull
	@Column(name = "SCORE", nullable = false)
	private int score;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name = "COMMENT", columnDefinition = "TEXT")
	private String comment;

	public Rating() {
	}

	public Rating(final User user, final Candidate candidate, final int score) {
		this.user = Objects.requireNonNull(user);
		this.candidate = Objects.requireNonNull(candidate);
		this.score = Objects.requireNonNull(score);
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public String getComment() {
		return comment;
	}

	public int getScore() {
		return score;
	}

	public User getUser() {
		return user;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final Rating other = (Rating) obj;
		return Objects.equals(candidate, other.candidate) && Objects.equals(score, other.score)
				&& Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(candidate, score, user);
		return result;
	}

	@Override
	public String toString() {
		return user + ", candidate=" + candidate + ", score=" + score;
	}
}
