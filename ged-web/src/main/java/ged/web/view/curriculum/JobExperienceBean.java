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

	public String cancel() {
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
	}

	public String delete() {
		if (!this.isNewJobExperience()) {
			this.jobExperienceService.delete(this.jobExperience);
		}
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
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

	@PostConstruct
	public void init() {
		this.jobExperience = this.getValueFromFlash(JOB_EXPERIENCE_KEY);
		if (this.jobExperience == null) {
			final Curriculum curriculum = this.getValueFromFlash(CURRICULUM_KEY);
			this.jobExperience = new JobExperience();
			this.jobExperience.setStillWorking(false);
			this.jobExperience.setCurriculum(curriculum);
		}
		if (this.jobExperience.getStartDate() != null) {
			this.fromDate = YearMonthDto.from(this.jobExperience.getStartDate());
		}
		if (this.jobExperience.getEndDate() != null) {
			this.toDate = YearMonthDto.from(this.jobExperience.getEndDate());
		}
		this.stillWorking = this.jobExperience.isStillWorking();
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public boolean isStillWorking() {
		return stillWorking;
	}

	public String save() {
		final YearMonth endDate = YearMonth.of(this.toDate.getYear(), this.toDate.getMonth());
		this.jobExperience.setEndDate(endDate);
		final YearMonth startDate = YearMonth.of(this.fromDate.getYear(), this.fromDate.getMonth());
		this.jobExperience.setStartDate(startDate);
		this.jobExperience.setStillWorking(stillWorking);
		this.jobExperience = this.jobExperienceService.save(this.jobExperience);
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
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
