package es.nivel36.laie.ejb.core.subject;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactDao;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class SubjectService {

	private @Inject SubjectDao subjectDao;
	private @Inject UserDao userDao;
	private @Inject CandidateDao candidateDao;
	private @Inject ContactDao contactDao;

	public Subject findByEmail(final String email) {
		final User user = userDao.findUserByEmail(email);
		if (user != null) {
			return user;
		}

		final Candidate candidate = candidateDao.findCandidateByEmail(email);
		if (candidate != null) {
			return candidate;
		}

		final Contact contact = contactDao.findContactByEmail(email);
		if (contact != null) {
			return contact;
		}
		return null;
	}

	public List<Subject> search(final String query) {
		Objects.requireNonNull(query);
		return subjectDao.searchSubject(query);
	}

	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = Objects.requireNonNull(subjectDao);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao);
	}

	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = Objects.requireNonNull(candidateDao);
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = Objects.requireNonNull(contactDao);
	}
	
	
}
