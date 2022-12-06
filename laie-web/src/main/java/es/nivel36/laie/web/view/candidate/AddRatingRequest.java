package es.nivel36.laie.web.view.candidate;

import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddRatingRequest extends AbstractView {

	private static final long serialVersionUID = -5291060183292266892L;
	
	private static final Logger logger = LoggerFactory.getLogger(AddRatingRequest.class);
	
	private @Param(required = true) Candidate candidate;
	
	private User requestTo;
	
	private String notes;
	
	protected transient @Inject UserService userService;
	
	public void send() {
		logger.debug("Send request ACTION performed");
	}
	
	public List<User> query(final String query) {
		logger.trace("Search user with the string {}", query);
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).hits();
	}

	public User getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(User requestTo) {
		this.requestTo = requestTo;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}