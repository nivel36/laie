package ged.web.view.curriculum;

import java.time.YearMonth;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.jobexperience.JobExperience;
import ged.ejb.curriculum.jobexperience.JobExperienceService;
import ged.web.core.YearMonthDto;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobExperienceBean extends AbstractDialogBean {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final String JOB_EXPERIENCE_KEY = "jobExperience";

	private static final long serialVersionUID = -2896828087283592020L;

	@NotNull
	private YearMonthDto fromDate;

	private JobExperience jobExperience;

	@Inject
	private transient JobExperienceService jobExperienceService;

	private boolean stillWorking;

	@NotNull
	private YearMonthDto toDate;

	public String delete() {
		this.jobExperienceService.delete(this.jobExperience);
		return curriculumUrl();
	}

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectUrl(this.jobExperience.getCurriculum().getCandidate());
	}

	public YearMonthDto getFromDate() {
		return this.fromDate;
	}

	public JobExperience getJobExperience() {
		return this.jobExperience;
	}

	public YearMonthDto getToDate() {
		return this.toDate;
	}

	public YearMonthDto initFromDate() {
		YearMonth fromDate = this.jobExperience.getStartDate();
		if (fromDate != null) {
			return YearMonthDto.of(fromDate);
		} else {
			return new YearMonthDto();
		}
	}

	public YearMonthDto initToDate() {
		YearMonth toDate = this.jobExperience.getEndDate();
		if (toDate != null) {
			return YearMonthDto.of(toDate);
		} else {
			return new YearMonthDto();
		}
	}

	public JobExperience initJobExperience() {
		JobExperience jobExperience = this.getValueFromFlash(JOB_EXPERIENCE_KEY);
		if (jobExperience == null) {
			final Curriculum curriculum = this.getValueFromFlash(CURRICULUM_KEY);
			jobExperience = new JobExperience();
			jobExperience.setStillWorking(false);
			jobExperience.setCurriculum(curriculum);
		}
		return jobExperience;
	}

	@PostConstruct
	public void init() {
		this.jobExperience = initJobExperience();
		this.fromDate = initFromDate();
		this.toDate = initToDate();
		this.stillWorking = this.jobExperience.isStillWorking();
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public boolean isStillWorking() {
		return stillWorking;
	}

	public String save() {
		if (this.stillWorking) {
			this.jobExperience.setEndDate(null);
		} else {
			final YearMonth endDate = YearMonth.of(this.toDate.getYear(), this.toDate.getMonth());
			this.jobExperience.setEndDate(endDate);
		}
		final YearMonth startDate = YearMonth.of(this.fromDate.getYear(), this.fromDate.getMonth());
		this.jobExperience.setStartDate(startDate);
		this.jobExperience.setStillWorking(stillWorking);
		this.jobExperience = this.jobExperienceService.save(this.jobExperience);
		return curriculumUrl();
	}

	public void setFromDate(final YearMonthDto fromDate) {
		this.fromDate = fromDate;
	}

	public void setJobExperience(final JobExperience jobExperience) {
		this.jobExperience = jobExperience;
	}

	public void setJobExperienceService(final JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}

	public void setStillWorking(boolean stillWorking) {
		this.stillWorking = stillWorking;
	}

	public void setToDate(final YearMonthDto toDate) {
		this.toDate = toDate;
	}
}
