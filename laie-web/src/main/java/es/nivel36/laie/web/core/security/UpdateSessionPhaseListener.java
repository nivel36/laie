package es.nivel36.laie.web.core.security;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import es.nivel36.laie.ejb.core.SessionUsers;
import jakarta.inject.Inject;

public class UpdateSessionPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -118131663560399880L;

	@Inject
	private transient SessionUsers sessionUsers;

	@Override
	public void afterPhase(final PhaseEvent event) {
		final FacesContext facesContext = event.getFacesContext();
		final ExternalContext externalContext = facesContext.getExternalContext();
		final Map<String, Object> sessionMap = externalContext.getSessionMap();
		final String username = (String) sessionMap.get("username");
		if (username != null) {
			this.sessionUsers.action(username);
		}
	}

	@Override
	public void beforePhase(final PhaseEvent event) {
		// Nothing to do
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
