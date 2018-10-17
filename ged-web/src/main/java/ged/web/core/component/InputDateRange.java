package ged.web.core.component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@FacesComponent(value="inputDateRange")
public class InputDateRange extends UIInput {

	private static final String FILE_NAME = "ged.i18n";

	private static Locale getLocale() {
		final UIViewRoot uIViewRoot = FacesContext.getCurrentInstance().getViewRoot();
		final Locale locale;
		if (uIViewRoot != null) {
			locale = uIViewRoot.getLocale();
		} else {
			locale = Locale.ENGLISH;
		}
		return locale;
	}

	private static ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	private static String message(final String message) {
		final ResourceBundle bundle = getResourceBundle(FILE_NAME);
		return bundle.getString(message);
	}

	private List<String> months;

	private List<Integer> years;

	public InputDateRange() {
		super();
		buildMonthsCombo();
		buildYearsCombo();
	}

	private void buildMonthsCombo() {
		this.months = new ArrayList<>();
		for (int i = 1; i < 13; i++) {
			final String nameOfMonth = message("date.month." + i);
			this.months.add(nameOfMonth);
		}
	}

	private void buildYearsCombo() {
		int presentYear = LocalDate.now().getYear();
		int minRange = presentYear - 50;
		int maxRange = presentYear + 50;
		this.years = new ArrayList<>();
		for (int i = minRange; i < maxRange; i++) {
			this.years.add(presentYear);
		}
	}

	public List<String> getMonths() {
		return this.months;
	}

	public List<Integer> getYears() {
		return this.years;
	}
	
	public boolean validateDates(final FacesContext context, final List<UIInput> components, final List<Object> values) {
		boolean inputStillWorking = false;
		Integer inputStartYear = null;
		Integer inputEndYear = null;

		final int size = components.size();
		for (int i = 0; i < size; i++) {
			final UIInput component = components.get(i);
			if (component.getId().equals("stillWorking")) {
				if (values.get(i) == null) {
					inputStillWorking = false;
				}
				else {
					inputStillWorking = (Boolean) values.get(i);
				}
			}
			else if (component.getId().equals("startYear")) {
				inputStartYear = (Integer) values.get(i);
			}
			else if (component.getId().equals("endYear")) {
				inputEndYear = (Integer) values.get(i);
			}
		}
		if (inputStillWorking) {
			return true;
		}
		if ((inputStartYear - inputEndYear) > 0) {
			return false;
		}
		return true;
	}

}