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
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobExperienceBean extends AbstractDialogBean {

	private static final String JOB_EXPERIENCE_KEY = "jobExperience";

	private static final String CURRICULUM_KEY = "curriculum";

	private static final long serialVersionUID = -2896828087283592020L;

	@NotNull
	private Integer endMonth;

	@NotNull
	private Integer endYear;

	private JobExperience jobExperience;

	@Inject
	private transient JobExperienceService jobExperienceService;

	@NotNull
	private Integer startMonth;

	@NotNull
	private Integer startYear;

	public String delete() {
		if (!this.isNewJobExperience()) {
			this.jobExperienceService.delete(this.jobExperience);
		}
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
	}

	public Integer getEndMonth() {
		return this.endMonth;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	public JobExperience getJobExperience() {
		return this.jobExperience;
	}

	public Integer getStartMonth() {
		return this.startMonth;
	}

	public Integer getStartYear() {
		return this.startYear;
	}
	
	public String cancel() {
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
	}

	@PostConstruct
	public void init() {
		this.jobExperience = this.getValueFromFlash(JOB_EXPERIENCE_KEY);
		if (this.jobExperience == null) {
			final Curriculum curriculum = getValueFromFlash(CURRICULUM_KEY);
			this.jobExperience = new JobExperience();
			this.jobExperience.setStillWorking(false);
			this.jobExperience.setCurriculum(curriculum);
		}
		if (this.jobExperience.getStartDate() != null) {
			this.startYear = this.jobExperience.getStartDate().getYear();
			this.startMonth = this.jobExperience.getStartDate().getMonthValue();
		}
		if (this.jobExperience.getEndDate() != null) {
			this.endYear = this.jobExperience.getEndDate().getYear();
			this.endMonth = this.jobExperience.getEndDate().getMonthValue();
		}
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.getId() == 0;
	}

	public String save() {
		if ((this.endYear != null) && (this.endMonth != null)) {
			final YearMonth endDate = YearMonth.of(this.endYear, this.endMonth);
			this.jobExperience.setEndDate(endDate);
		}
		if ((this.startYear != null) && (this.startMonth != null)) {
			final YearMonth startDate = YearMonth.of(this.startYear, this.startMonth);
			this.jobExperience.setStartDate(startDate);
		}
		this.jobExperience = this.jobExperienceService.save(this.jobExperience);
		this.putValueToFlash(CURRICULUM_KEY, this.jobExperience.getCurriculum());
		return PageEnum.CURRICULUM.getRedirectUrl();
	}

	public void setEndMonth(final Integer endMonth) {
		this.endMonth = endMonth;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setJobExperience(final JobExperience jobExperience) {
		this.jobExperience = jobExperience;
	}

	public void setJobExperienceService(final JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}

	public void setStartMonth(final Integer startMonth) {
		this.startMonth = startMonth;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
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
