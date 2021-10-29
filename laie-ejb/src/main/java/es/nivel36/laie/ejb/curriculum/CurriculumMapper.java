package es.nivel36.laie.ejb.curriculum;

import java.util.Set;

import es.nivel36.laie.ejb.core.Mapper;
import es.nivel36.laie.ejb.curriculum.education.Education;
import es.nivel36.laie.ejb.curriculum.education.EducationDto;
import es.nivel36.laie.ejb.curriculum.education.EducationMapper;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperience;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceDto;
import es.nivel36.laie.ejb.curriculum.jobexperience.JobExperienceMapper;
import es.nivel36.laie.ejb.curriculum.language.Language;
import es.nivel36.laie.ejb.curriculum.language.LanguageDto;
import es.nivel36.laie.ejb.curriculum.language.LanguageMapper;
import es.nivel36.laie.ejb.curriculum.skill.Skill;
import es.nivel36.laie.ejb.curriculum.skill.SkillMapper;

public class CurriculumMapper implements Mapper<Curriculum, CurriculumDto> {

	@Override
	public CurriculumDto map(final Curriculum entity) {
		if (entity == null) {
			return null;
		}
		final CurriculumDto dto = new CurriculumDto();
		dto.setUid(entity.getUid());

		final Set<Education> educations = entity.getEducation();
		final EducationMapper educationMapper = new EducationMapper();
		final Set<EducationDto> educationDtos = educationMapper.mapSet(educations);
		dto.setEducation(educationDtos);

		final Set<JobExperience> jobExperiences = entity.getJobExperiences();
		final JobExperienceMapper jobExperienceMapper = new JobExperienceMapper();
		final Set<JobExperienceDto> jobExperienceDtos = jobExperienceMapper.mapSet(jobExperiences);
		dto.setJobExperiences(jobExperienceDtos);

		final Set<Language> languages = entity.getLanguages();
		final LanguageMapper languageMapper = new LanguageMapper();
		final Set<LanguageDto> languagesDto = languageMapper.mapSet(languages);
		dto.setLanguages(languagesDto);

		final Set<Skill> skills = entity.getSkills();
		final SkillMapper skillMapper = new SkillMapper();
		final Set<String> skillDtos = skillMapper.mapSet(skills);
		dto.setSkills(skillDtos);

		return dto;
	}
}
