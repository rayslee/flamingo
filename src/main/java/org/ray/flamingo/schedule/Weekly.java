package org.ray.flamingo.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
public class Weekly extends Schedule{
	
	@NotNull
	private LocalTime start;
	
	@NotNull
	private LocalTime finish;
	
	@ElementCollection
	private Set<DayOfWeek> weeks = new HashSet<>(7);
	
	public Weekly(String name, LocalTime start, LocalTime finish, Set<DayOfWeek> weeks) {
		super(name);
		this.start  = start;
		this.finish = finish;
		this.weeks  = weeks;
	}
	
	@Override
	public String toString() {
		return "Weekly [name=" + name + ", start=" + start + ", finish=" + finish + ", weeks=" + weeks + "]";
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
		return other.everActiveDuring(start, finish) && other.everActiveDuring(weeks);
	}

	@Override
	public boolean everActiveDuring(Set<DayOfWeek> weeks) {
		return new HashSet<>(weeks).removeAll(this.weeks);
	}
	
	@Override
	public boolean everActiveDuring(LocalTime begin, LocalTime end) {
		return weeks.isEmpty() ? false : !super.freeFromEachCouple(start, finish, begin, end);
	}
	
	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end) {
		return super.containsAnyDayOfWeekDuring(begin, end, weeks);
	}

	@Override
	public boolean everActiveDuring(LocalDate begin, LocalDate end, Set<DayOfWeek> weeks) {
		return everActiveDuring(weeks);
	}

}
