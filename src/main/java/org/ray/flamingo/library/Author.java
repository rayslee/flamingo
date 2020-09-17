package org.ray.flamingo.library;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class Author {
	
	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	private Long id;
	
	@NotNull
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	private Set<AuthorLink> authorLinks = new HashSet<>();

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}

}
