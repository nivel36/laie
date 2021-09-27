package es.nivel36.laie.web.view.curriculum;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.curriculum.JobExperience;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.YearMonthDto;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

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

	private JobExperience jobExperience;

	@NotNull
	private YearMonthDto toDate;

	private JobExperience buildJobExperienceFromQueryParameter(final String itemParameter) {
		try {
			final int item = Integer.parseInt(itemParameter);
			final List<JobExperience> jobExperiences = new ArrayList<>(this.curriculum.getJobExperiences());
			if (item >= jobExperiences.size()) {
				throw new IllegalPageStateException();
			}
			Collections.sort(jobExperiences);
			return jobExperiences.get(item);
		} catch (final NumberFormatException e) {
			throw new IllegalPageStateException();
		}
	}

	private JobExperience buildNewJobExperience() {
		JobExperience newJobExperience = new JobExperience();
		newJobExperience.setStillWorking(false);
		newJobExperience.setCurriculum(this.curriculum);
		return newJobExperience;
	}

	private String buildCurriculumUrl() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("id", this.curriculum.getUid());
		queryParams.put("candidateId", this.curriculum.getCandidate().getUid());
		return this.navigator.getRedirectUrl(PageEnum.CURRICULUM, queryParams);
	}

	public String delete() {
		this.curriculum.removeJobExperience(this.jobExperience);
		this.curriculumService.save(this.curriculum);
		return this.buildCurriculumUrl();
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
		this.toDate = this.initToDate();
		this.fromDate = this.initFromDate();
	}

	public YearMonthDto initFromDate() {
		final YearMonth jobExperienceFromDate = this.jobExperience.getStartDate();
		if (jobExperienceFromDate != null) {
			return YearMonthDto.of(jobExperienceFromDate);
		} else {
			return new YearMonthDto();
		}
	}

	public JobExperience initJobExperience() {
		final String itemParameter = this.getValueFromGetParameters("item");
		if (itemParameter == null) {
			return this.buildNewJobExperience();
		} else {
			return this.buildJobExperienceFromQueryParameter(itemParameter);
		}
	}

	public YearMonthDto initToDate() {
		final YearMonth jobExperienceEndDate = this.jobExperience.getEndDate();
		if (jobExperienceEndDate != null) {
			return YearMonthDto.of(jobExperienceEndDate);
		} else {
			return new YearMonthDto();
		}
	}

	public boolean isNewJobExperience() {
		return this.jobExperience.isNew();
	}

	public String save() {
		this.updateJobExperienceData();
		if (jobExperience.isNew()) {
			this.curriculum.addJobExperience(this.jobExperience);
		}
		this.curriculumService.save(this.curriculum);
		return this.buildCurriculumUrl();
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
