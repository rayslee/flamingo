package org.ray.flamingo.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Habitus extends Schedule {
	
	@NotNull
	private LocalTime start;
	
	@NotNull
	private LocalTime finish;
	
	public Habitus(String name, LocalTime start, LocalTime finish) {
		super(name);
		this.start  = start;
		this.finish = finish;
	}
	
	@Override
	public String toString() {
		return "Habitus [name=" + name +  ", start=" + start + ", finish=" + finish + "]";
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
		return other.everActiveDuring(start, finish);
	}

	@Override
	public boolean everActiveDuring(Set<DayOfWeek> weeks) {
		return true;
	}
	
	@Override
	public boolean everActiveDuring(LocalTime begin, LocalTime end) {
		return !super.freeFromEachCouple(start, finish, begin, end);
	}
	
	@Override
	public boolean everActiveDuring(LocalDate start, LocalDate finish) {
		return true;
	}
	
	@Override
	public boolean everActiveDuring(LocalDate start, LocalDate finish, Set<DayOfWeek> weeks) {
		return true;
	}

}
