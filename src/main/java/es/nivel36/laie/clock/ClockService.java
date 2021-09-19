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
