package ged.web.core;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

public class YearMonthDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public static YearMonthDto of(final YearMonth yearMonth) {
		Objects.requireNonNull(yearMonth);
		final YearMonthDto dto = new YearMonthDto();
		dto.month = Integer.valueOf(yearMonth.getMonthValue());
		dto.year = Integer.valueOf(yearMonth.getYear());
		return dto;
	}

	private Integer month;

	private Integer year;

	public YearMonthDto() {
	}

	public YearMonthDto(final Integer month, final Integer year) {
		this.month = month;
		this.year = year;
	}

	public Integer getMonth() {
		return this.month;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setMonth(final Integer month) {
		this.month = month;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}
}
