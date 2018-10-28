package ged.web.core.component;

import java.io.IOException;
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

	private String[] buildMonthsCombo() {
		final String[] months = new String[12];
		for (int i = 1; i < 13; i++) {
			final String nameOfMonth = message("date.month." + i);
			months[i - 1] = nameOfMonth;
		}
		return months;
	}

	private Integer[] buildYearsCombo(final int minusYear, final int plusYear) {
		final int presentYear = LocalDate.now().getYear();
		final int range = (plusYear + minusYear);
		final int maxYear = presentYear + plusYear;
		final Integer[] years = new Integer[range];
		for (int i = 0; i < range; i++) {
			years[i] = maxYear - i;
		}
		return years;
	}

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		final int maxYear = (int) getAttributes().get("plusYear");
		final int minYear = (int) getAttributes().get("minusYear");
		setMonths(buildMonthsCombo());
		setYears(buildYearsCombo(minYear, maxYear));
		super.encodeBegin(context);
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	private Locale getLocale() {
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

	public String[] getMonths() {
		return (String[]) getStateHelper().get("months");
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	public Integer[] getYears() {
		return (Integer[]) getStateHelper().get("years");
	}

	private String message(final String message) {
		final ResourceBundle bundle = getResourceBundle(FILE_NAME);
		return bundle.getString(message);
	}

	public void setMonths(final String[] months) {
		getStateHelper().put("months", months);
	}

	public void setYears(final Integer[] years) {
		getStateHelper().put("years", years);
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
			else if (component.getId().equals("fromYear")) {
				inputStartYear = (Integer) values.get(i);
			}
			else if (component.getId().equals("toYear")) {
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