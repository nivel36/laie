package es.nivel36.laie.view;

import java.time.LocalDateTime;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.nivel36.laie.clock.ClockService;
import es.nivel36.laie.core.view.AbstractView;

@Named
@ViewScoped
public class ClockView extends AbstractView {

	private static final long serialVersionUID = -4469554886861035880L;

	private ClockService clockService;
	
	private String userUid;
	
	public void init() {
		this.getParamater("userUid");
	}

	public void signin() {
		clockService.clockIn(userUid, LocalDateTime.now());
	}

	public void signout() {
		clockService.clockOut(userUid, LocalDateTime.now());
	}
}
