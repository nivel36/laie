package ged.web.core;

import java.time.YearMonth;

public class YearMonthDto {

	public static YearMonthDto from(final YearMonth yearMonth) {
		final YearMonthDto dto = new YearMonthDto();
		dto.month = Integer.valueOf(yearMonth.getMonthValue());
		dto.year = Integer.valueOf(yearMonth.getYear());
		return dto;
	}

	private Integer month;

	private Integer year;

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
