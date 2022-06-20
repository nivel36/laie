package es.nivel36.laie.ejb.core.subject;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactDao;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class SubjectService {

	private @Inject SubjectDao subjectDao;

	private @Inject UserDao userDao;

	private @Inject CandidateDao candidateDao;

	private @Inject ContactDao contactDao;

	public Subject findByEmail(String email) {
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

	public List<Subject> search(String query) {
		Objects.requireNonNull(query);
		return subjectDao.searchSubject(query);
	}
}
