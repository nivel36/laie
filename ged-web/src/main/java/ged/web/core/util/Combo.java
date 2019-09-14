package ged.web.core.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named
public class Combo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> months;

	@Inject
	private transient Translator translator;

	private List<Integer> years;

	private void buildMonthsCombo() {
		this.months = new ArrayList<>();
		for (int i = 1; i < 13; i++) {
			final String nameOfMonth = this.translator.message("date.month." + i);
			this.months.add(nameOfMonth);
		}
	}

	private void buildYearsCombo() {
		final Integer presentYear = LocalDate.now().getYear();
		this.years = new ArrayList<>();
		this.years.add(presentYear);
		for (int i = 1; i < 50; i++) {
			this.years.add(presentYear - i);
		}
	}

	public List<String> getMonths() {
		return this.months;
	}

	public List<Integer> getYears() {
		return this.years;
	}

	@PostConstruct
	public void init() {
		this.buildMonthsCombo();
		this.buildYearsCombo();
	}

}
