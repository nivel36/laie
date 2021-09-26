package es.nivel36.laie.view;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.nivel36.laie.clock.ClockService;
import es.nivel36.laie.core.view.AbstractView;

@Named
@ViewScoped
public class ClockView extends AbstractView {

	private static final long serialVersionUID = 6122112404121158778L;

	private ClockService clockService;
	
	private String userUid;
	
	@PostConstruct
	public void init() {
		userUid = this.getParamater("userUid");
	}
	
	public boolean renderSignin() {
		return true;
	}
	
	public boolean renderSignout() {
		return false;
	}

	public void signin() {
		clockService.clockIn(userUid, LocalDateTime.now());
	}

	public void signout() {
		clockService.clockOut(userUid, LocalDateTime.now());
	}
}
