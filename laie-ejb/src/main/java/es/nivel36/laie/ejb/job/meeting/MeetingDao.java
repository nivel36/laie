package es.nivel36.laie.ejb.job.meeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MeetingDao extends AbstractDao {

	public List<Meeting> findByAttendeesEmail(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT m
				FROM Meeting m
				WHERE m.datePlanned >= current_date
				AND :email MEMBER OF m.attendeesEmails
				ORDER BY m.datePlanned ASC
				""";
		final TypedQuery<Meeting> query = this.em.createQuery(jpql, Meeting.class);
		query.setParameter("email", email);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<Meeting> findMonthMeetingsByUser(final User user, final LocalDateTime date) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(date);
		final String jpql = """
				SELECT m
				FROM Meeting m
				WHERE m.datePlanned >= :startDate
				AND m.datePlanned <= :endDate
				AND :email MEMBER OF m.attendeesEmails
				ORDER BY m.datePlanned ASC
				""";
		final TypedQuery<Meeting> query = this.em.createQuery(jpql, Meeting.class);
		query.setParameter("email", user.getEmail());
		final LocalDateTime startDate = date.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		final LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT m
				FROM Meeting m
				WHERE m.owner = :owner
				AND m.datePlanned < current_date
				ORDER BY m.datePlanned ASC
				""";
		final TypedQuery<Meeting> query = this.em.createQuery(jpql, Meeting.class);
		query.setParameter("owner", owner);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countConductedMeetings(final User owner) {
		Objects.requireNonNull(owner);
		final String jpql = """
				SELECT COUNT(m)
				FROM Meeting m
				WHERE m.owner = :owner
				AND m.datePlanned < current_date
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("owner", owner);
		return query.getSingleResult();
	}

	public List<Meeting> findFutureMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT m
				FROM Meeting m
				WHERE m.owner = :owner 
				AND m.datePlanned >= current_date
				ORDER BY m.datePlanned ASC
				""";
		final TypedQuery<Meeting> query = this.em.createQuery(jpql, Meeting.class);
		query.setParameter("owner", owner);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countFutureMeetings(final User owner) {
		Objects.requireNonNull(owner);
		final String jpql = """
				SELECT COUNT(m) 
				FROM Meeting m
				WHERE m.owner = :owner
				AND m.datePlanned >= current_date
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("owner", owner);
		return query.getSingleResult();
	}
}