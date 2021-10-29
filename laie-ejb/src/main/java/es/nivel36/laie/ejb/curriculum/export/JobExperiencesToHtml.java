package es.nivel36.laie.ejb.curriculum.export;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;

public class JobExperiencesToHtml extends AbstractHtmlPrinter {

	String print(final Set<JobExperience> jobExperiences) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("jobExperiences"));
		sb.append("<h1>Experiencia laboral</h1>");
		sb.append(this.openDiv("data"));
		for (final JobExperience jobExperience : jobExperiences) {
			sb.append(this.printJobExperience(jobExperience));
		}
		sb.append(this.closeDiv());
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printCompanyName(final String companyName) {
		return this.openDiv("companyName") + companyName + this.closeDiv();
	}

	String printDate(final YearMonth start, final YearMonth end, final boolean stillWorking) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM / yy");
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("from-to-date")).append(this.openSpan("from-date")).append(start.format(formatter))
				.append(this.closeSpan()).append(" - ");
		if (end != null) {
			sb.append(this.openSpan("to-date")).append(end.format(formatter)).append(this.closeSpan());
		}
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printDescription(final String description) {
		return this.openDiv("description") + description + this.closeDiv();
	}

	String printJobExperience(final JobExperience jobExperience) {
		return this.openDiv("jobExperience") + this.printJobPosition(jobExperience.getJobPosition())
				+ this.printDate(jobExperience.getStartDate(), jobExperience.getEndDate(),
						jobExperience.isStillWorking())
				+ this.printCompanyName(jobExperience.getCompanyName())
				+ this.printDescription(jobExperience.getDescription()) + this.closeDiv();
	}

	String printJobPosition(final String jobPosition) {
		return this.openDiv("jobPosition") + jobPosition + this.closeDiv();
	}

}
