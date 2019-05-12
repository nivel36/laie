package ged.web.view.curriculum;

import java.time.YearMonth;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
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
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public String save() {
		final YearMonth endDate = YearMonth.of(this.toDate.getYear(), this.toDate.getMonth());
		this.jobExperience.setEndDate(endDate);
		final YearMonth startDate = YearMonth.of(this.fromDate.getYear(), this.fromDate.getMonth());
		this.jobExperience.setStartDate(startDate);
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

	public void setToDate(final YearMonthDto toDate) {
		this.toDate = toDate;
	}

	public boolean validateDates(final FacesContext context, final List<UIInput> components,
			final List<Object> values) {
		boolean inputStillWorking = false;
		Integer inputStartMonth = null;
		Integer inputStartYear = null;
		Integer inputEndMonth = null;
		Integer inputEndYear = null;

		final int size = components.size();
		for (int i = 0; i < size; i++) {
			final UIInput component = components.get(i);
			if (component.getId().equals("stillWorking")) {
				if (values.get(i) == null) {
					inputStillWorking = false;
				} else {
					inputStillWorking = (Boolean) values.get(i);
				}
			} else if (component.getId().equals("startMonth")) {
				inputStartMonth = (Integer) values.get(i);
			} else if (component.getId().equals("startYear")) {
				inputStartYear = (Integer) values.get(i);
			} else if (component.getId().equals("endMonth")) {
				inputEndMonth = (Integer) values.get(i);
			} else if (component.getId().equals("endYear")) {
				inputEndYear = (Integer) values.get(i);
			}
		}
		if (inputStillWorking) {
			return true;
		}
		final YearMonth startDate = YearMonth.of(inputStartYear, inputStartMonth);
		final YearMonth endDate = YearMonth.of(inputEndYear, inputEndMonth);
		if (startDate.isAfter(endDate)) {
			return false;
		}
		return true;
	}
}
