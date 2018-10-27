package ged.web.core.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@FacesComponent(value = "inputDateRange")
public class InputDateRange extends UIInput implements NamingContainer {

	private static final String FILE_NAME = "ged.i18n";

	private Integer getMaxYear() {
		return (Integer) getAttributes().get("maxYear");
	}
	
	private Integer getMinYear() {
		return (Integer) getAttributes().get("minYear");
	}

	private static Locale getLocale() {
		final UIViewRoot uIViewRoot = FacesContext.getCurrentInstance().getViewRoot();
		final Locale locale;
		if (uIViewRoot != null) {
			locale = uIViewRoot.getLocale();
		}
		else {
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

	private UIInput fromMonth;

	private UIInput fromYear;

	private UIInput toMonth;

	private UIInput toYear;

	public InputDateRange() {
		super();
		buildMonthsCombo();
		buildYearsCombo();
	}

	private String[] buildMonthsCombo() {
		final String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			final String nameOfMonth = message("date.month." + i);
			months[i] = nameOfMonth;
		}
		return months;
	}

<<<<<<< HEAD
	private Integer[] buildYearsCombo() {
		final int presentYear = LocalDate.now().getYear();
		final int minRange = presentYear - 50;
		final int maxRange = presentYear + 50;
		final int range = maxRange - minRange;
		final Integer[] years = new Integer[range];
		for (int i = 0; i < range; i++) {
			years[i] = minRange + i;
=======
	private void buildYearsCombo() {
		int presentYear = LocalDate.now().getYear();
		int minRange = presentYear - getMinYear();
		int maxRange = presentYear + getMaxYear();
		this.years = new ArrayList<>();
		for (int i = maxRange; i > minRange; i--) {
			this.years.add(i);
>>>>>>> branch 'develop' of https://aferrer74@bitbucket.org/ged_team/ged.git
		}
		return years;
	}

<<<<<<< HEAD
	public Integer[] getMonths() {
		return (Integer[]) getStateHelper().get("months");
=======
	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public List<String> getMonths() {
		return this.months;
>>>>>>> branch 'develop' of https://aferrer74@bitbucket.org/ged_team/ged.git
	}

	public Integer[] getYears() {
		return (Integer[]) getStateHelper().get("years");
	}

<<<<<<< HEAD
	public void setMonths(final Integer[] months) {
		getStateHelper().put("months", months);
	}

	public void setYears(final Integer[] years) {
		getStateHelper().put("years", years);
	}

	public boolean validateDates(final FacesContext context, final List<UIInput> components, final List<Object> values) {
=======
	public boolean validateDates(final FacesContext context, final List<UIInput> components,
			final List<Object> values) {
>>>>>>> branch 'develop' of https://aferrer74@bitbucket.org/ged_team/ged.git
		boolean inputStillWorking = false;
		Integer inputStartYear = null;
		Integer inputEndYear = null;

		final int size = components.size();
		for (int i = 0; i < size; i++) {
			final UIInput component = components.get(i);
			if (component.getId().equals("stillWorking")) {
				if (values.get(i) == null) {
					inputStillWorking = false;
				} else {
					inputStillWorking = (Boolean) values.get(i);
				}
			} else if (component.getId().equals("startYear")) {
				inputStartYear = (Integer) values.get(i);
			} else if (component.getId().equals("endYear")) {
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