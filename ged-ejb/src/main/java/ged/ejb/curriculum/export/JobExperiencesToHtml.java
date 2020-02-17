package ged.ejb.curriculum.export;

import java.time.YearMonth;
import java.util.Set;

import ged.ejb.curriculum.JobExperience;

public class JobExperiencesToHtml extends AbstractHtmlPrinter {

	String print(final Set<JobExperience> jobExperiences) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("jobExperiences"));
		for (final JobExperience jobExperience : jobExperiences) {
			sb.append(this.printJobExperience(jobExperience));
		}
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printCompanyName(final String companyName) {
		return this.openDiv("companyName") + companyName + this.closeDiv();
	}

	String printDate(final YearMonth start, final YearMonth end, final boolean stillWorking) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("from-to-date")).append("(").append(this.openSpan("from-date")).append(start)
				.append(this.closeSpan()).append(" - ");
		if (end != null) {
			sb.append(this.openSpan("to-date")).append(end).append(this.closeSpan());
		}
		sb.append(")").append(this.closeDiv());
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
