package es.nivel36.laie.ejb.curriculum.export;

import java.util.Objects;

import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.curriculum.Curriculum;

public class CurriculumToHtml extends AbstractHtmlPrinter {
	
	private FileService fileService;

	public CurriculumToHtml(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	String print(final Curriculum curriculum, String imagePath) {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.openDiv("curriculum"));
		sb.append(new ContactDataToHtml(this.fileService).print(curriculum.getCandidate(), imagePath));
		sb.append(new JobExperiencesToHtml().print(curriculum.getJobExperiences()));
		sb.append(new EducationToHtml().print(curriculum.getEducation()));
		sb.append(new LanguagesToHtml().print(curriculum.getLanguages()));
		sb.append(new SkillsToHtml().print(curriculum.getSkills()));
		sb.append(this.closeDiv());
		return sb.toString();
	}
}
