package org.ray.flamingo.controller;

import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;
import org.ray.flamingo.security.User;
import org.ray.flamingo.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/node")
public class NodeController {
	
	@Autowired
	private NodeService ns;
	
	//ADMIN REQUESTS
	
	@GetMapping("/admin/start")
	public String checkById(Model model, 
						   @AuthenticationPrincipal User user) {
		return "redirect:/node/admin/check/" + user.getId();
	}
	
	@GetMapping("/admin/check/{id}")
	public String checkById(Model model, 
							@PathVariable("id") Long id) {
		Node result = ns.find(id).get();
		
		model.addAttribute("node", result);
		model.addAttribute("services", Module.values());// unregisteredModules(result));
		
		return "node";
	}
	
	@GetMapping("/admin/modify/{id}/attr/append")
	public String appendAttributeById(Model model,
									 @PathVariable("id")  Long id,
									 @RequestParam("key") String key,
									 @RequestParam("val") String val) {
		ns.appendAttribute(id, key, val);
		
		return "redirect:/node/admin/check/" + id;
	}
	
	@GetMapping("/admin/modify/{parentId}/child/append")
	public String appendChildWithNameById(Model model,
									 @PathVariable("parentId")   Long   parentId,
									 @RequestParam("childName")  String childName) {
		ns.appendChild(parentId, childName);
		
		return "redirect:/node/admin/check/" + parentId;
	}
	
	@GetMapping("/admin/modify/{parentId}/child/remove/{childId}")
	public String removeSon(Model model,
							@PathVariable("parentId") Long parentId,
							@PathVariable("childId") Long childId) {
		ns.remove(childId);	//everything on child node will be removed all together
		
		return "redirect:/node/admin/check/" + parentId;
	}
	
	//GUEST REQUESTS
	

}
