package ged.ejb.curriculum.export;

import ged.ejb.candidate.Candidate;

public class ContactDataToHtml extends AbstractHtmlPrinter {

	String print(final Candidate candidate, String imagePath) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("name-profile"));
		sb.append(this.printName(candidate.getFullName()));
		sb.append(this.printJobProfile(candidate.getJobProfile()));
		if (candidate.getImageFileName() != null) {
			sb.append(printPicture(imagePath + "/" + candidate.getImageFileName()));
		}
		sb.append(closeDiv());
		sb.append(this.openDiv("contact"));
		sb.append("<h1>Datos de contacto</h1>");
		sb.append(this.openDiv("data"));
		sb.append(this.printEmail(candidate.getEmail()));
		sb.append(this.printPhone(candidate.getPhoneNumber()));
		sb.append(this.closeDiv());
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printPicture(String path) {
		return "<div class=\"user-image\"><img class=\"picture\" src=\"" + path + "\"/></div>";
	}

	String printEmail(final String email) {
		return this.openDiv("email") + printLabel("Correo electrónico") + email + this.closeDiv();
	}

	String printLabel(final String label) {
		return this.openSpan("label") + label + this.closeSpan();
	}

	String printName(final String name) {
		return this.openDiv("name") + printLabel("Nombre") + name + this.closeDiv();
	}
	
	String printJobProfile(final String jobProfile) {
		return this.openDiv("jobProfile") + printLabel("Perfil") + jobProfile + this.closeDiv();
	}

	String printPhone(final String phone) {
		return this.openDiv("phone") + printLabel("Teléfono") + phone + this.closeDiv();
	}
}
