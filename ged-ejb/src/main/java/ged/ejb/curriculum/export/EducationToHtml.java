package ged.ejb.curriculum.export;

import ged.ejb.curriculum.Education;

class EducationToHtml {

	String toHtml(final Education education) {		
		return "<div class=\"education\">" + printDegree(education.getDegree()) + "</div>";
	}

	String printDegree(String degree) {
		return "<span class=\"degree\">" + degree + "</span>";
	}
	
	String printDescription(String description) {
		return "<p class=\"description\">" + description + "</p>";
	}
}
