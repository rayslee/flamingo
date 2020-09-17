package org.ray.flamingo.repository;

import java.util.List;

import org.ray.flamingo.library.Book;
import org.springframework.data.domain.Pageable;

public interface BookRepository extends BaseRepository<Book, String> {
	
	boolean existsByIsbn(String isbn);
	
	List<Book> findByTitle(String title, Pageable pageable);

}
