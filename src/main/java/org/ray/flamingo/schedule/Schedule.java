package org.ray.flamingo.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.index.Clue;

import lombok.AccessLevel;

/**
 * An activity which will take up a period of time.
 * 
 * @see Habitus
 * @see Weekly
 * @see Event
 * @see Hebdomad
 * @author Ray LEE
 * @since 1.0
 */
@Entity
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Schedule implements Clue {
	
	@ManyToOne
	protected Node depot;
	
	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	protected Long id;
	
	@NotNull
	protected String name;
	
	protected Schedule(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", name=" + name + "]";
	}
	
	//ABSTRACT METHODS
	
	public abstract boolean isOn();
	
	public abstract boolean isValid();
	
	public abstract boolean collide(Schedule other);
	
	public abstract boolean everActiveDuring(Set<DayOfWeek> weeks);
	
	public abstract boolean everActiveDuring(LocalTime start, LocalTime finish);
	
	public abstract boolean everActiveDuring(LocalDate start, LocalDate finish);
	
	public abstract boolean everActiveDuring(LocalDate start, LocalDate finish, Set<DayOfWeek> weeks);
	
	//PROTECTED METHODS
	
	protected final LocalDate fetchLater(LocalDate d1, LocalDate d2) {
		return d1.isAfter(d2) ? d1 : d2;
	}
	
	protected final LocalDate fetchEarlier(LocalDate d1, LocalDate d2) {
		return d1.isBefore(d2) ? d1 : d2;
	}
	
	protected final boolean containsNow(LocalTime start, LocalTime finish) {
		return !LocalTime.now().isBefore(start) 
					&& !LocalTime.now().isAfter(finish);
	}
	
	protected final boolean containsNow(LocalDateTime start, LocalDateTime finish) {
		return !LocalDateTime.now().isBefore(start) 
					&& !LocalDateTime.now().isAfter(finish);
	}
	
	protected final boolean containsToday(Set<DayOfWeek> dayOfWeeks) {
		return dayOfWeeks.contains(LocalDate.now().getDayOfWeek());
	}
	
	protected final boolean isUpsideDown(LocalTime start, LocalTime finish) {
		return !start.isAfter(finish);		//'start.equals(finish) == true' is allowed
	}
	
	protected final boolean isUpsideDown(LocalDateTime start, LocalDateTime finish) {
		LocalTime tStart  = start.toLocalTime();
		LocalTime tFinish = finish.toLocalTime();
		
		if(tStart.isAfter(tFinish))			//'tStart.equals(tFinish) == true' is allowed
			return false;
		
		return !start.isAfter(finish);		//'start.equals(finish) == true' is allowed
	}
	
	protected final boolean containsAnyDayOfWeekDuring(LocalDate start, LocalDate finish, Set<DayOfWeek> weeks) {
		int i = 0;
		
		while(!start.isAfter(finish) && i < 7) {
			if(weeks.contains(start.getDayOfWeek()))
				return true;
			
			start = start.plusDays(1);		//generate new object cause its immutable
		}
		
		return false;
	}
	
	protected final boolean freeFromEachCouple(LocalTime start, LocalTime finish, LocalTime begin, LocalTime end) {
		return !start.isBefore(end) || !finish.isAfter(begin);
	}
	
	protected final boolean freeFromEachCouple(LocalDate start, LocalDate finish, LocalDate begin, LocalDate end) {
		return start.isAfter(end) || finish.isBefore(begin);
	}
	
	//AS Clue
	
	@Override
	public String getContent() {
		return toString();
	}
	
}






































//mysql> select * from habitus;
//+------+------+----------+----------+----------+
//| id   | name | depot_id | finish   | start    |
//+------+------+----------+----------+----------+
//| 8081 | dasd |     1910 | 12:11:00 | 11:11:00 |
//+------+------+----------+----------+----------+
//1 row in set (0.00 sec)

//mysql> select * from weekly;
//+------+------+----------+----------+----------+
//| id   | name | depot_id | finish   | start    |
//+------+------+----------+----------+----------+
//| 8080 | sacc |     1910 | 23:04:00 | 21:23:00 |
//+------+------+----------+----------+----------+
//1 row in set (0.00 sec)
//
//mysql> select * from event;
//+------+------+----------+---------------------+---------------------+
//| id   | name | depot_id | finish              | start               |
//+------+------+----------+---------------------+---------------------+
//| 8082 | sasa |     1910 | 2019-04-24 15:11:00 | 2019-04-03 14:11:00 |
//+------+------+----------+---------------------+---------------------+
//1 row in set (0.00 sec)
//
//mysql> select * from hebdomad;
//+------+------+----------+---------------------+---------------------+
//| id   | name | depot_id | finish              | start               |
//+------+------+----------+---------------------+---------------------+
//| 8080 | sasa |     1910 | 2019-04-12 08:03:00 | 2019-04-02 22:11:00 |
//+------+------+----------+---------------------+---------------------+
//1 row in set (0.00 sec)