package es.nivel36.laie.web.core.view.component;

import java.time.Duration;
import java.time.LocalDateTime;

import es.nivel36.laie.web.core.util.Translator;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UINamingContainer;

@FacesComponent(value = "events")
public class Events extends UIInput implements NamingContainer {
	
	private Translator translator = new Translator();

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public String elapsedTime(final LocalDateTime startTime) {
	    LocalDateTime now = LocalDateTime.now();
	    Duration duration = Duration.between(startTime, now);

	    long seconds = duration.getSeconds();
	    long minutes = duration.toMinutes();
	    long hours = duration.toHours();
	    long days = duration.toDays();
	    long months = days / 30;
	    long years = months / 12;

	    if (years > 1) return translator.message("date.years", new Object[] {years});
	    if (years == 1) return translator.message("date.year", new Object[] {years});
	    if (months > 1) return translator.message("date.months", new Object[] {months});
	    if (months == 1) return translator.message("date.month", new Object[] {months});
	    if (days > 1) return translator.message("date.days", new Object[] {days});
	    if (days == 1) return translator.message("date.day", new Object[] {days});
	    if (hours > 1) return translator.message("date.hours", new Object[] {hours});
	    if (hours == 1) return translator.message("date.hour", new Object[] {hours});
	    if (minutes > 1) return translator.message("date.minutes", new Object[] {minutes});
	    if (minutes == 1) return translator.message("date.minute", new Object[] {minutes});
	    if (seconds > 1) return translator.message("date.seconds", new Object[] {seconds});
	    return translator.message("date.second", new Object[] {seconds});
	}
}
