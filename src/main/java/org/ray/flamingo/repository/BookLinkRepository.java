package org.ray.flamingo.repository;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.library.BookLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookLinkRepository extends BaseRepository<BookLink, BookLink.Id> {
	
	Page<BookLink> findByDepot(Node depot, Pageable pageable);
	
	boolean existsById(BookLink.Id id);
	
}
