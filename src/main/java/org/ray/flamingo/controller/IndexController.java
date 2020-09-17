package org.ray.flamingo.controller;

import org.ray.flamingo.index.Broker;
import org.ray.flamingo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/index")
@SessionAttributes("broker")
public class IndexController {
	
	@Autowired
	private IndexService is;
	
	@GetMapping("/check/{id}")
	public String enterIndexPage(Model model, 
								@PathVariable("id") long id) {
		
		model.addAttribute("id", id);
		
		
		return "index";
	}
	
	@GetMapping("/{id}")
	public String takeUserRequest(RedirectAttributes attr,
								 @PathVariable("id") Long nodeId,
								 @RequestParam("value") String value) {
		Broker broker = is.findBroker(nodeId, value);
		
		attr.addFlashAttribute("broker", broker);
		
		return "redirect:/index/broker";
	}
	
	@GetMapping("/broker")
	public String indexResultsByBroker(Model model, 
							 		  @ModelAttribute("broker") Broker broker) {
		is.askForMoreMessage(broker);
		
		model.addAttribute("id", broker.getNodeId());
		model.addAttribute("results", broker.getResults());
		
		return "index";
	}

}
