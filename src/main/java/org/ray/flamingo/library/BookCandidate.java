package org.ray.flamingo.library;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@lombok.Data
public class BookCandidate {
	
	@Id
	private long libraryId;
	
	@NotNull
	private String isbn;
	
	@NotNull
	private String title;
	
	@NotNull
	private String author;

}
