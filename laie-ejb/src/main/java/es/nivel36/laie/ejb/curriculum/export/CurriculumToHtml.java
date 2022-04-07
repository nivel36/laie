package es.nivel36.laie.ejb.curriculum.export;

import es.nivel36.laie.ejb.curriculum.Curriculum;

public class CurriculumToHtml extends AbstractHtmlPrinter {

	String print(final Curriculum curriculum, String imagePath) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("curriculum"));
		sb.append(new ContactDataToHtml().print(curriculum.getCandidate(), imagePath));
		sb.append(new JobExperiencesToHtml().print(curriculum.getJobExperiences()));
		sb.append(new EducationToHtml().print(curriculum.getEducation()));
		sb.append(new LanguagesToHtml().print(curriculum.getLanguages()));
		sb.append(new SkillsToHtml().print(curriculum.getSkills()));
		sb.append(this.closeDiv());
		return sb.toString();
	}
}
