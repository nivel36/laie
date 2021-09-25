/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.clock;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.user.User;
import es.nivel36.laie.user.UserJpaDao;
import es.nivel36.laie.user.UserService;

@Stateless
public class ClockService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Inject
	@Repository
	private ClockJpaDao clockDao;

	@Inject
	@Repository
	private UserJpaDao userDao;

	public void clockIn(final String userUid, final LocalDateTime time) {
		final User user = this.userDao.findUserByUid(userUid);
		final ClockRecord clockRecord = new ClockRecord(ClockType.IN, time, user);
		this.clockDao.insert(clockRecord);
	}

	public void clockOut(final String userUid, final LocalDateTime time) {
		final User user = this.userDao.findUserByUid(userUid);
		final ClockRecord clockRecord = new ClockRecord(ClockType.IN, time, user);
		this.clockDao.insert(clockRecord);
	}

	public List<ClockRecord> findClockRecords(final String userUid, final LocalDateTime start,
			final LocalDateTime end) {
		Objects.requireNonNull(userUid);
		logger.debug("Find clock record by user {}", userUid);
		return this.clockDao.findClockRecords(userUid, start, end);
	}

	public List<ClockRecord> findMonthClockRecords(final String userUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Find clock record by user {}", userUid);
		final LocalDateTime end = LocalDateTime.now();
		final LocalDateTime start = end.withDayOfMonth(1);
		return this.clockDao.findClockRecords(userUid, start, end);
	}

	public List<ClockRecord> findTodayClockRecords(final String userUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Find clock record by user {}", userUid);
		final LocalDateTime end = LocalDateTime.now();
		final LocalDateTime start = end.with(LocalTime.MIN);
		return this.clockDao.findClockRecords(userUid, start, end);
	}

	public List<ClockRecord> findWeekClockRecords(final String userUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Find clock record by user {}", userUid);
		final LocalDateTime end = LocalDateTime.now();
		final LocalDateTime start = end.with(DayOfWeek.MONDAY);
		return this.clockDao.findClockRecords(userUid, start, end);
	}
}
