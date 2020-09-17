package org.ray.flamingo.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Event extends Schedule {
	
	@NotNull
	private LocalDateTime start;
	
	@NotNull
	private LocalDateTime finish;
	
	public Event(String name, LocalDateTime start, LocalDateTime finish) {
		super(name);
		this.start  = start;
		this.finish = finish;
	}
	
	@Override
	public String toString() {
		return "Event [name=" + name + ", start=" + start + ", finish=" + finish + "]";
	}
	
	@Override
	public boolean isOn() {
		return super.containsNow(start, finish);
	}
	
	@Override 
	public boolean isValid() {
		return super.isUpsideDown(start, finish);
	}
	
	@Override
	public boolean collide(Schedule other) {
		return other.everActiveDuring(start.toLocalTime(), finish.toLocalTime()) 
				&& other.everActiveDuring(start.toLocalDate(), finish.toLocalDate());
	}

	@Override
	public boolean everActiveDuring(Set<DayOfWeek> weeks) {
		return super.containsAnyDayOfWeekDuring(start.toLocalDate(), finish.toLocalDate(), weeks);
	}

	@Override
	public boolean everActiveDuring(LocalTime begin, LocalTime end) {
		return !(!start.toLocalTime().isBefore(end) || !finish.toLocalTime().isAfter(begin));
	}
	
	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end) {
		return !super.freeFromEachCouple(start.toLocalDate(), finish.toLocalDate(), begin, end);
	}

	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end, Set<DayOfWeek> weeks) {
		LocalDate startD  = start.toLocalDate();
		LocalDate finishD = finish.toLocalDate();
		
		if(super.freeFromEachCouple(startD, finishD, begin, end))
			return false;
		
		return super.containsAnyDayOfWeekDuring(super.fetchLater(startD, begin), 
												super.fetchEarlier(finishD, end), 
												weeks);
	}

}
