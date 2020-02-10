package ged.web.view.curriculum;

import java.time.YearMonth;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.Param;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.JobExperience;
import ged.web.core.YearMonthDto;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class JobExperienceView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "curriculumId", required = true)
	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	@NotNull
	private YearMonthDto fromDate;

	@Inject
	@Param(name = "id", required = false)
	private JobExperience jobExperience;

	@NotNull
	private YearMonthDto toDate;

	private String curriculumUrl() {
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, this.curriculum);
	}

	public String delete() {
		this.curriculum.removeJobExperience(this.jobExperience);
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
		this.jobExperience = this.initJobExperience();
		this.fromDate = this.initFromDate();
		this.toDate = this.initToDate();
		if (!this.jobExperience.isNew()) {
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
		if (this.jobExperience == null) {
			this.jobExperience = new JobExperience();
			this.jobExperience.setStillWorking(false);
			this.jobExperience.setCurriculum(this.curriculum);
		}
		return this.jobExperience;
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
		return this.jobExperience.isNew();
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

	public void setToDate(final YearMonthDto toDate) {
		this.toDate = toDate;
	}

	private void updateJobExperienceData() {
		if (this.jobExperience.isStillWorking()) {
			this.jobExperience.setEndDate(null);
		} else {
			final YearMonth endDate = YearMonth.of(this.toDate.getYear(), this.toDate.getMonth());
			this.jobExperience.setEndDate(endDate);
		}
		final YearMonth startDate = YearMonth.of(this.fromDate.getYear(), this.fromDate.getMonth());
		this.jobExperience.setStartDate(startDate);
	}
}
