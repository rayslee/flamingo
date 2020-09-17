package org.ray.flamingo.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Hebdomad extends Schedule {
	
	@NotNull
	private LocalDateTime start;
	
	@NotNull
	private LocalDateTime finish;
	
	@ElementCollection
	private Set<DayOfWeek> weeks = new HashSet<>(7);
	
	public Hebdomad(String name, LocalDateTime start, LocalDateTime finish, Set<DayOfWeek> weeks) {
		super(name);
		this.start  = start;
		this.finish = finish;
		this.weeks  = weeks;
	}
	
	@Override
	public String toString() {
		return "Hebdomad [name=" + name + ", start=" + start + ", finish=" + finish + ", weeks=" + weeks + "]";
	}
	
	@Override
	public boolean isOn() {
		return super.containsToday(weeks) && super.containsNow(start, finish);
	}
	
	@Override 
	public boolean isValid() {
		return super.isUpsideDown(start, finish);
	}
	
	@Override
	public boolean collide(Schedule other) {
		return other.everActiveDuring(start.toLocalTime(), finish.toLocalTime()) 
				&& other.everActiveDuring(start.toLocalDate(), finish.toLocalDate(), weeks);
	}

	@Override
	public boolean everActiveDuring(Set<DayOfWeek> dayOfWeeks) {
		Set<DayOfWeek> weeks = new HashSet<>(dayOfWeeks);
		weeks.retainAll(this.weeks);	//retain the intersection of weeks
		
		LocalDateTime temp = start;
		int i = 0;
		while(!temp.isAfter(finish) && i < 7) {
			if(weeks.contains(temp.getDayOfWeek()))
				return true;
			
			temp = temp.plusDays(1);	//generate new object cause its immutable
		}
		
		return false;
	}
	
	@Override
	public boolean everActiveDuring(LocalTime begin, LocalTime end) {
		return weeks.isEmpty() ? false : 
								 !super.freeFromEachCouple(start.toLocalTime(), finish.toLocalTime(), begin, end);
	}
	
	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end) {
		return super.containsAnyDayOfWeekDuring(begin, end, weeks);
	}
	
	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end, Set<DayOfWeek> dayOfWeeks) {
		Set<DayOfWeek> weeks = new HashSet<>(dayOfWeeks);
		weeks.retainAll(this.weeks);
		
		LocalDate startD  = start.toLocalDate();
		LocalDate finishD = finish.toLocalDate();
		
		if(super.freeFromEachCouple(startD, finishD, begin, end))
			return false;
		else 
			return super.containsAnyDayOfWeekDuring(super.fetchLater(startD, begin), 
													super.fetchEarlier(finishD, end), 
													weeks);
	}

}
