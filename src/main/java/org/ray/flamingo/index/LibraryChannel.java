package org.ray.flamingo.index;

import java.util.ArrayList;
import java.util.List;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.library.Author;
import org.ray.flamingo.library.IndexType;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class LibraryChannel implements Channel {
	
	//PART ONE
	
	private List<Node> libraries;
	
	private IndexType indexType;
	
	private String indexValue;
	
	public LibraryChannel(List<Node> libraries, IndexType indexType, String indexValue) {
		this.libraries  = libraries;
		this.indexType  = indexType;
		this.indexValue = indexValue;
	}
	
	//PART TWO
	
	private List<Clue> clues = new ArrayList<>();
	
	private Pageable pageable;
	
	private List<Author> authors;
	
	private int authPos;

	@Override
	public List<Clue> withdrawClues() {
		List<Clue> temp = clues;
		
		clues = new ArrayList<>();
		
		return temp;
	}

}
