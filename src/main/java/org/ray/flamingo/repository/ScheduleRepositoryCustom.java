package org.ray.flamingo.repository;

import java.util.List;

import org.ray.flamingo.schedule.Schedule;

interface ScheduleRepositoryCustom {
	
	List<Schedule> findSchedulesByDepotId(Long id);
	
	void saveScheduleByDepotId(Long id, Schedule schedule);
	
}
