package org.ray.flamingo.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.ray.flamingo.PageProps;
import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;
import org.ray.flamingo.form.BookForm;
import org.ray.flamingo.index.Channel;
import org.ray.flamingo.index.Clue;
import org.ray.flamingo.index.Indexable;
import org.ray.flamingo.index.LibraryChannel;
import org.ray.flamingo.library.Author;
import org.ray.flamingo.library.Book;
import org.ray.flamingo.library.BookCandidate;
import org.ray.flamingo.library.BookLink;
import org.ray.flamingo.library.IndexType;
import org.ray.flamingo.repository.AuthorLinkRepository;
import org.ray.flamingo.repository.AuthorRepository;
import org.ray.flamingo.repository.BookCandidateRepository;
import org.ray.flamingo.repository.BookLinkRepository;
import org.ray.flamingo.repository.BookRepository;
import org.ray.flamingo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LibraryService implements Indexable {
	
	//VIEW METHODS

	@Autowired private PageProps pageProps;
	
	@Autowired private NodeRepository nodeRepo;
	
	@Autowired private BookLinkRepository bookLinkRepo;
	
	public Page<BookLink> getBooksOfFirstPage(long nodeId) {
		return bookLinkRepo.findByDepot(
								nodeRepo.findById(nodeId).get(), 
								PageRequest.of(0, pageProps.getSize()));
	}
	
	public Page<Book> getBooksByPageable(long nodeId, Pageable pageable) {
		
		
		return null;
	}
	
	//APPEND METHODS
	
	@Autowired private BookRepository bookRepo;
	
	@Autowired private ModuleService moduService;
	
	public void appendByIsbn(long nodeId, String isbn) {
		Optional<Book> book = bookRepo.findById(isbn);
		if(book.isPresent() == false)
			return;
		
		bookLinkRepo.save(new BookLink(nodeRepo.findById(nodeId).get(), book.get()));
		log.info(book.get() + " added into library " + nodeId);
		
		moduService.register(nodeId, Module.LIBRARY);
	}
	
	@Autowired private BookCandidateRepository bookCandRepo;
	
	public void appendBookCandidate(long nodeId, BookForm form) {
		BookCandidate candidate = new BookCandidate();
		
		candidate.setLibraryId(nodeId);
		candidate.setIsbn(form.getIsbn());
		candidate.setTitle(form.getTitle());
		candidate.setAuthor(form.getAuthor());
		
		bookCandRepo.save(candidate);
	}
	
	//Methods of Indexable
	
	@Override
	public Channel buildConnection(long startPos, String message) {
		List<Node> libraries = nodeRepo.roverLibraries(startPos);
		
		IndexType indexType  = parseUserInput(message);
		
		return new LibraryChannel(libraries, indexType, message);
	}
	
	@Override
	public Channel stuffClues(Channel channel, int floorSize) {
		LibraryChannel libChan = (LibraryChannel) channel;
		
		switch(libChan.getIndexType()) {
			case ISBN   : return indexByIsbn(libChan);
			case TITLE  : return indexByTitle(libChan, floorSize);
			case AUTHOR : return indexByAuthor(libChan, floorSize);
			
			//u should always override all index types' processing procedures to avoid being here
			default : throw new UnsupportedOperationException("Oops: unsupported indexing type.");
		}
	}
	
	//PRIVATE METHODS
	
	@Autowired private AuthorRepository authRepo;
	
	private IndexType parseUserInput(String input) {
		if(bookRepo.existsByIsbn(input))
			return IndexType.ISBN;
		
		if(authRepo.existsByName(input))
			return IndexType.AUTHOR;
		
		return IndexType.TITLE;
	}
	
	//ISBN
	
	private Channel indexByIsbn(LibraryChannel libChan) {
		List<Clue> clues = libChan.getClues();
		
		for(Node library : libChan.getLibraries()) {
			Optional<BookLink> link = bookLinkRepo.findById(new BookLink.Id(library.getId(), libChan.getIndexValue()));
			if(link.isPresent())
				clues.add(link.get());
		}
		
		return libChan;
	}
	
	//TITILE
	
	private Channel indexByTitle(LibraryChannel libChan, int floorSize) {
		List<Book> books = new ArrayList<>();
		
		while(books.size() < floorSize) {
			books = findMoreBooksByTitle(libChan);
			if(books.isEmpty()) break;
			siftIntoChannel(libChan, books);
		}
		
		 return libChan;
	}
	
	private List<Book> findMoreBooksByTitle(LibraryChannel libChan) {
		Pageable page = libChan.getPageable();
		if(page == null)
			page = PageRequest.of(0, pageProps.getLoadSize());
		else 
			page = page.next();
		
		/*
		 * Page-able class is immutable (create new object instead of changing state)
		 * So do not forget to reset it every time u make change
		 */
		libChan.setPageable(page);
		
		return bookRepo.findByTitle(libChan.getIndexValue(), page);
	}
	
	private LibraryChannel siftIntoChannel(LibraryChannel libChan, List<Book> books) {
		List<Clue> clues = libChan.getClues();
		
		for(Node library : libChan.getLibraries())
			for(Book book : books) {
				BookLink.Id linkId = new BookLink.Id(library.getId(), book.getIsbn());
				Optional<BookLink> link = bookLinkRepo.findById(linkId);
				if(link.isPresent())
					clues.add(link.get());
			}
		
		return libChan;
	}
	
	//AUTHOR
	
	@Autowired private AuthorLinkRepository authLinkRepo;
	
	private Channel indexByAuthor(LibraryChannel libChan, int floorSize) {
		List<Book> books = new ArrayList<>();
		
		while(books.size() < floorSize) {
			books = loadMoreBooksOfNextAuthor(libChan);
			if(books.isEmpty()) break;
			siftIntoChannel(libChan, books);
		}
		
		return libChan;
	}
	
	private List<Book> loadMoreBooksOfNextAuthor(LibraryChannel libChan) {
		if(libChan.getAuthors() == null)
			libChan.setAuthors(
							authRepo.findByName(
											libChan.getIndexValue()));
		
		List<Book> books = new ArrayList<>();
		while(books.isEmpty()) {
			Optional<Author> author = getNextAuthor(libChan);
			if(author.isPresent() == false) return Collections.emptyList();
			books = authLinkRepo.findByAuthor(author.get())
								.stream()
								.map(link -> link.getBook())
								.collect(toList());
		}
		
		return books;
	}
	
	private Optional<Author> getNextAuthor(LibraryChannel libChan) {
		List<Author> authors = libChan.getAuthors();
		int authPos = libChan.getAuthPos();
		if(authPos >= authors.size())
			return Optional.empty();
		
		Author author = authors.get(authPos);
		libChan.setAuthPos(++authPos);
		
		return Optional.of(author);
	}
	
}
