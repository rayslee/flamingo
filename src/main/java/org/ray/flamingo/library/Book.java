package org.ray.flamingo.library;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@lombok.Getter
@lombok.Setter
public class Book {
	
	@Id
	@Column(length = 25)
	private String isbn;
	
	@NotNull
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	private Set<BookLink> bookLinks = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	private Set<AuthorLink> authorLinks = new HashSet<>();

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + "]";
	}

}
