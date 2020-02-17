package ged.ejb.curriculum.export;

import java.util.Set;

import ged.ejb.curriculum.Education;

class EducationToHtml extends AbstractHtmlPrinter {

	String print(final Set<Education> educations) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("educations"));
		for (final Education education : educations) {
			sb.append(this.printEducation(education));
		}
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printDate(final Integer start, final Integer end, final boolean stillStudying) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("from-to-date")).append("(").append(this.openSpan("from-date")).append(start)
				.append(this.closeSpan()).append(" - ");
		if (end != null) {
			sb.append(this.openSpan("to-date")).append(end).append(this.closeSpan());
		}
		sb.append(")").append(this.closeDiv());
		return sb.toString();
	}

	String printDegree(final String degree) {
		return this.openDiv("degree") + degree + this.closeDiv();
	}

	String printDescription(final String description) {
		return this.openDiv("description") + description + this.closeDiv();
	}

	String printEducation(final Education education) {
		return this.openDiv("education") + this.printDegree(education.getDegree())
				+ this.printDate(education.getStartYear(), education.getEndYear(), education.isStillStudying())
				+ this.printSchool(education.getSchool()) + this.printDescription(education.getDescription())
				+ this.closeDiv();
	}

	String printSchool(final String school) {
		return this.openDiv("school") + school + this.closeDiv();
	}
}
