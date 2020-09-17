package org.ray.flamingo.form;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ScheduleForm {
	
	@NotBlank(message = "field is required")
	public String name;
	
	@DateTimeFormat(pattern="HH:mm")
	public LocalTime startTime;
	
	@DateTimeFormat(pattern="HH:mm")
	public LocalTime finishTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public LocalDate startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public LocalDate finishDate;
	
	public Set<DayOfWeek> weeks = new HashSet<>(7);

}
