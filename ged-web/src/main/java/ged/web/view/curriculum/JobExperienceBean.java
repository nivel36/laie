package ged.web.view.curriculum;

import java.time.YearMonth;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.JobExperience;
import ged.web.core.YearMonthDto;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobExperienceBean extends AbstractBean {

	private static final String CURRICULUM_KEY = "curriculum";

	private static final String JOB_EXPERIENCE_KEY = "jobExperience";

	private static final long serialVersionUID = -2896828087283592020L;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	@NotNull
	private YearMonthDto fromDate;

	private JobExperience jobExperience;

	private boolean stillWorking;

	@NotNull
	private YearMonthDto toDate;

	private String curriculumUrl() {
		return PageEnum.CURRICULUM.getRedirectUrl(this.jobExperience.getCurriculum().getCandidate());
	}

	public String delete() {
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
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
		this.curriculum = this.getValueFromFlash(CURRICULUM_KEY);
		this.jobExperience = this.initJobExperience();
		this.fromDate = this.initFromDate();
		this.toDate = this.initToDate();
		this.stillWorking = this.jobExperience.isStillWorking();
		if (!this.isNewJobExperience()) {
			this.curriculum.removeJobExperience(this.jobExperience);
		}
	}

	public YearMonthDto initFromDate() {
		final YearMonth fromDate = this.jobExperience.getStartDate();
		if (fromDate != null) {
			return YearMonthDto.of(fromDate);
		} else {
			return new YearMonthDto();
		}
	}

	public JobExperience initJobExperience() {
		JobExperience jobExperience = this.getValueFromFlash(JOB_EXPERIENCE_KEY);
		if (jobExperience == null) {
			jobExperience = new JobExperience();
			jobExperience.setStillWorking(false);
			jobExperience.setCurriculum(this.curriculum);
		}
		return jobExperience;
	}

	public YearMonthDto initToDate() {
		final YearMonth toDate = this.jobExperience.getEndDate();
		if (toDate != null) {
			return YearMonthDto.of(toDate);
		} else {
			return new YearMonthDto();
		}
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public boolean isStillWorking() {
		return this.stillWorking;
	}

	public String save() {
		this.updateJobExperienceData();
		this.curriculum.addJobExperience(this.jobExperience);
		this.curriculumService.save(this.curriculum);
		return this.curriculumUrl();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setFromDate(final YearMonthDto fromDate) {
		this.fromDate = fromDate;
	}

	public void setJobExperience(final JobExperience jobExperience) {
		this.jobExperience = jobExperience;
	}

	public void setStillWorking(final boolean stillWorking) {
		this.stillWorking = stillWorking;
	}

	public void setToDate(final YearMonthDto toDate) {
		this.toDate = toDate;
	}

	private void updateJobExperienceData() {
		if (this.stillWorking) {
			this.jobExperience.setEndDate(null);
		} else {
			final YearMonth endDate = YearMonth.of(this.toDate.getYear(), this.toDate.getMonth());
			this.jobExperience.setEndDate(endDate);
		}
		final YearMonth startDate = YearMonth.of(this.fromDate.getYear(), this.fromDate.getMonth());
		this.jobExperience.setStartDate(startDate);
		this.jobExperience.setStillWorking(this.stillWorking);
	}
}
