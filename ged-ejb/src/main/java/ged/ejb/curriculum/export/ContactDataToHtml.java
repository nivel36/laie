package ged.ejb.curriculum.export;

import ged.ejb.candidate.Candidate;

public class ContactDataToHtml extends AbstractHtmlPrinter {

	String print(final Candidate candidate) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("contactData"));
		sb.append("<h1>Datos de contacto</h1>");
		sb.append(this.printName(candidate.getFullName()));
		sb.append(this.printEmail(candidate.getEmail()));
		sb.append(this.printPhone(candidate.getPhoneNumber()));
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printEmail(final String email) {
		return this.openDiv("email") + email + this.closeDiv();
	}

	String printName(final String name) {
		return this.openDiv("name") + name + this.closeDiv();
	}

	String printPhone(final String phone) {
		return this.openDiv("phone") + phone + this.closeDiv();
	}

}
