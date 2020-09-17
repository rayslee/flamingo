package org.ray.flamingo.controller;

import org.ray.flamingo.form.BookForm;
import org.ray.flamingo.library.BookLink;
import org.ray.flamingo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library")
public class LibraryController {
	
	@Autowired
	private LibraryService ls;
	
	//ADMIN REQUESTS
	
	@GetMapping("/admin/check/{id}")
	public String libraryWelcomPage(Model model,
								   @PathVariable("id") long id) {
		
		Page<BookLink> firstPage = ls.getBooksOfFirstPage(id);
		
		model.addAttribute("page", firstPage);
		model.addAttribute("bookForm", new BookForm());
		model.addAttribute("nodeId", id);
		
		return "library";
	}
	
	@GetMapping("/admin/modify/{id}/append/isbn")
	public String appendBookByIsbn(Model model,
								  @PathVariable("id") long nodeId,
								  @RequestParam("isbn") String isbn) {
		ls.appendByIsbn(nodeId, isbn);
		
		return "redirect:/library/admin/check/" + nodeId;
	}
	
	@PostMapping("/admin/modify/{id}/append/book")
	public String appendBook(Model model,
						    @PathVariable("id") long nodeId,
							@ModelAttribute("bookForm") BookForm form) {
		ls.appendBookCandidate(nodeId, form);
		
		return "redirect:/library/admin/check/" + nodeId;
	}

}
