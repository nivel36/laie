package ged.ejb.curriculum.export;

import ged.ejb.curriculum.Curriculum;

public class CurriculumToHtml extends AbstractHtmlPrinter {

	String print(final Curriculum curriculum) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("curriculum"));
		sb.append(new ContactDataToHtml().print(curriculum.getCandidate()));
		sb.append(new JobExperiencesToHtml().print(curriculum.getJobExperiences()));
		sb.append(new EducationToHtml().print(curriculum.getEducation()));
		sb.append(new LanguagesToHtml().print(curriculum.getLanguages()));
		sb.append(new SkillsToHtml().print(curriculum.getSkills()));
		sb.append(this.closeDiv());
		return sb.toString();
	}
}
