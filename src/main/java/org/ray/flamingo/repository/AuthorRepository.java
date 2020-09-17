package org.ray.flamingo.repository;

import java.util.List;

import org.ray.flamingo.library.Author;

public interface AuthorRepository extends BaseRepository<Author, Long> {
	
	boolean existsByName(String name);
	
	List<Author> findByName(String name);

}
