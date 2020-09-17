package org.ray.flamingo.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BookForm {
	
	@NotBlank(message = "field is required")
	private String isbn;
	
	@NotBlank(message = "field is required")
	private String title;
	
	@NotBlank(message = "field is required")
	private String author;

}
