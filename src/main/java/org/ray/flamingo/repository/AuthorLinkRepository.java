package org.ray.flamingo.repository;

import java.util.List;

import org.ray.flamingo.library.Author;
import org.ray.flamingo.library.AuthorLink;

public interface AuthorLinkRepository extends BaseRepository<AuthorLink, AuthorLink.Id>{

	List<AuthorLink> findByAuthor(Author author);
	
}
