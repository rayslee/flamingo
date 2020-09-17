package org.ray.flamingo.controller;

import java.time.DayOfWeek;
import java.util.List;

import javax.validation.Valid;

import org.ray.flamingo.form.ScheduleForm;
import org.ray.flamingo.schedule.Schedule;
import org.ray.flamingo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService es;
	
	//ADMIN REQUESTS
	
	@GetMapping("/admin/check/{depotId}")
	public String checkByDepotId(Model model,
								@PathVariable("depotId") Long depotId) {
		List<Schedule> schedules = es.getAllByDepotId(depotId);
		
		model.addAttribute("depotId", depotId);
		model.addAttribute("schedules", schedules);
		model.addAttribute("newbie", new ScheduleForm());
		model.addAttribute("dayOfWeeks", DayOfWeek.values());
		
		return "schedule";
	}
	
	@PostMapping("/admin/modify/{depotId}/append")
	public String appendNewbie(Model model,
							  @PathVariable("depotId") Long depotId,
							  @Valid @ModelAttribute("newbie") ScheduleForm newbie,
							  Errors errors) {
		if(errors.hasErrors())
			return "schedule";
		
		es.appendByDepotId(depotId, newbie);
		
		return "redirect:/schedule/admin/check/" + depotId;
	}

}
