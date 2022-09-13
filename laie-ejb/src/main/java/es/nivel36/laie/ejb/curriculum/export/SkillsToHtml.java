package es.nivel36.laie.ejb.curriculum.export;

import java.util.Set;

import es.nivel36.laie.ejb.curriculum.Skill;

class SkillsToHtml extends AbstractHtmlPrinter {

	String print(final Set<Skill> skills) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("skills"));
		sb.append("<h1>Habilidades</h1>");
		sb.append(this.openDiv("data"));
		for (final Skill skill : skills) {
			sb.append(this.printSkill(skill));
		}
		sb.append(this.closeDiv());
		sb.append(this.closeDiv());
		return sb.toString();
	}

	String printName(final String name) {
		return this.openSpan("name") + name + this.closeSpan();
	}

	String printSkill(final Skill skill) {
		return this.openDiv("skill") + this.printName(skill.getName()) + this.closeDiv();
	}
}
