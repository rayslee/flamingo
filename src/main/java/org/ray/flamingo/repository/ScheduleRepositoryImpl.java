package org.ray.flamingo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.schedule.Schedule;

class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Schedule> findSchedulesByDepotId(Long id) {
		return em.find(Node.class, id).getSchedules();
	}
	
	@Transactional
	@Override public void saveScheduleByDepotId(Long id, Schedule schedule) {
		Node depot = em.find(Node.class, id);
		
		if(depot != null) {
			schedule.setDepot(depot);
			em.persist(schedule);
		}
	}

}
