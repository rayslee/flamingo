package org.ray.flamingo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;
import org.ray.flamingo.form.ScheduleForm;
import org.ray.flamingo.index.Channel;
import org.ray.flamingo.index.Clue;
import org.ray.flamingo.index.Indexable;
import org.ray.flamingo.index.ScheduleChannel;
import org.ray.flamingo.repository.NodeRepository;
import org.ray.flamingo.repository.ScheduleRepository;
import org.ray.flamingo.schedule.Event;
import org.ray.flamingo.schedule.Habitus;
import org.ray.flamingo.schedule.Hebdomad;
import org.ray.flamingo.schedule.Schedule;
import org.ray.flamingo.schedule.Weekly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleService implements Indexable {
	private static final Module SCHEDULE = Module.SCHEDULE;
	
	@Autowired
	private ScheduleRepository sr;
	
	@Autowired
	private ModuleService ms;
	
	//PUBLIC METHODS
	
	public List<Schedule> getAllByDepotId(Long id) {
		return sr.findSchedulesByDepotId(id);
	}
	
	/*
	 * 1. validation checking
	 * 2. schedule transform
	 * 3. collision checking
	 * 4. module registration
	 * 5. schedule saving
	 */
	public void appendByDepotId(Long depotId, ScheduleForm form) {
		if(invalidUserInputAppears(form))
			return;
		
		form.name = examine(form.name);
		Schedule newbie = transfer(form);
		if(scheduleCollisionHappened(depotId, newbie))
			return;
		
		sr.saveScheduleByDepotId(depotId, newbie);
		ms.register(depotId, SCHEDULE);
		log.info("Depot " + depotId +  " added " + newbie);
	}
	
	//Date & time checking
	
	private boolean invalidUserInputAppears(ScheduleForm s) {
		if(isNull(s.startTime) || isNull(s.finishTime))
			return true;
		
		if(s.startTime.isAfter(s.finishTime))
			return true;
		
		if(notNull(s.startDate) && notNull(s.finishDate))
			if(s.startDate.isAfter(s.finishDate))
				return true;
		
		return false;
	}
	
	private boolean isNull(Object o) {
		return o == null;
	}
	
	private boolean notNull(Object o) {
		return o != null;
	}
	
	//Collision checking
	
	private boolean scheduleCollisionHappened(Long depotId, Schedule newbie) {
		List<Schedule> schedules = getAllByDepotId(depotId);
		
		for(Schedule schedule : schedules)
			if(newbie.collide(schedule))
				return true;
		
		return false;
	}
	
	//Form transformation
	
	private Schedule transfer(ScheduleForm f) {
		if((isNull(f.startDate) || isNull(f.finishDate)) && f.weeks.isEmpty())
			return habitus(f);
		
		else if(isNull(f.startDate) || isNull(f.finishDate))
			return  weekly(f);
		
		else if(f.weeks.isEmpty())
			return event(f);
		
		else
			return hebdomad(f);
	}
	
	private Schedule habitus(ScheduleForm f) {
		return new Habitus(f.name, f.startTime, f.finishTime);
	}
	
	private Schedule weekly(ScheduleForm f) {
		return new Weekly(f.name, f.startTime, f.finishTime, f.weeks);
	}
	
	private Schedule event(ScheduleForm f) {
		LocalDateTime start  = f.startDate.atTime(f.startTime);
		
		LocalDateTime finish = f.finishDate.atTime(f.finishTime);
		
		return new Event(f.name, start, finish);
	}
	
	private Schedule hebdomad(ScheduleForm f) {
		LocalDateTime start  = f.startDate.atTime(f.startTime);
		
		LocalDateTime finish = f.finishDate.atTime(f.finishTime);
		
		return new Hebdomad(f.name, start, finish, f.weeks);
	}
	
	//Methods of Indexable
	
	@Autowired
	private NodeRepository nr;
	
	@Override
	public Channel buildConnection(long startPos, String message) {
		return new ScheduleChannel(nr.roverSchedulers(startPos), message);
	}

	@Override
	public Channel stuffClues(Channel channel, int floorSize) {
		ScheduleChannel schChan = (ScheduleChannel) channel;
		List<Clue> clues  = schChan.getClues();
		List<Node> nodes  = schChan.getSchedulers();
		String indexValue = schChan.getIndexValue();
		
		while(clues.size() < floorSize && nodes.size() > 0) {
			Node last = nodes.remove(nodes.size() - 1);
			
			for(Schedule schedule : sr.findSchedulesByDepotId(last.getId()))
				if(schedule.getName().contains(indexValue))
					clues.add(schedule);
		}
		
		return channel;
	}
	
	//Anti pollution
	
	@Autowired private PollutionService pollServ;
	
	private String examine(String candidate) {
		return pollServ.replaceBadWords(candidate);
	}

}
