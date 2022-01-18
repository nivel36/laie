package es.nivel36.laie.web.core.view.component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import es.nivel36.laie.web.core.YearMonthDto;

@FacesComponent(value = "inputYearMonth")
public class InputYearMonth extends UIInput implements NamingContainer {

	private Integer year;

	private Integer month;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public static class Month {

		private String monthName;

		private Integer monthNumber;

		public String getMonthName() {
			return this.monthName;
		}

		public Integer getMonthNumber() {
			return this.monthNumber;
		}

		public void setMonthName(final String monthName) {
			this.monthName = monthName;
		}

		public void setMonthNumber(final Integer monthNumber) {
			this.monthNumber = monthNumber;
		}
	}

	private static final String FILE_NAME = "es.nivel36.laie.i18n";

	private Month[] buildMonthsCombo() {
		final Month[] months = new Month[12];
		for (int i = 1; i < 13; i++) {
			final Month newMonth = new Month();
			newMonth.setMonthName(this.message("date.month." + i));
			newMonth.setMonthNumber(i);
			months[i - 1] = newMonth;
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
		final int maxYear = (int) this.getAttributes().get("plusYear");
		final int minYear = (int) this.getAttributes().get("minusYear");
		this.setMonths(this.buildMonthsCombo());
		this.setYears(this.buildYearsCombo(minYear, maxYear));
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
		} else {
			locale = Locale.ENGLISH;
		}
		return locale;
	}

	public Month[] getMonths() {
		return (Month[]) this.getStateHelper().get("months");
	}

	@Override
	public Object getSubmittedValue() {
		return new YearMonthDto(month, year);
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	public Integer[] getYears() {
		return (Integer[]) this.getStateHelper().get("years");
	}

	private String message(final String message) {
		final ResourceBundle bundle = this.getResourceBundle(InputYearMonth.FILE_NAME);
		return bundle.getString(message);
	}

	public void setMonths(final Month[] months) {
		this.getStateHelper().put("months", months);
	}

	public void setYears(final Integer[] years) {
		this.getStateHelper().put("years", years);
	}
}
