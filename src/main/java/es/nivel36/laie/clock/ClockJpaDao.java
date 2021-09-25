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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.AbstractDao;
import es.nivel36.laie.core.service.Repository;

@Repository
public class ClockJpaDao extends AbstractDao {

	public List<ClockRecord> findClockRecords(final String userUid, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		Objects.requireNonNull(userUid);
		final TypedQuery<ClockRecord> q = this.entityManager.createNamedQuery("ClockRecord.findClockRecord",
				ClockRecord.class);
		q.setParameter("uid", userUid);
		q.setParameter("start", startDateTime);
		q.setParameter("end", endDateTime);
		return q.getResultList();
	}
	
	public void insert(ClockRecord clockRecord ) {
		this.entityManager.persist(clockRecord);
	}
}
