package org.ray.flamingo.index;

import java.util.List;

import lombok.Data;

@Data
public class NodeChannel implements Channel {
	
	private List<Clue> clues;
	
	public NodeChannel(List<Clue> nodes) {
		this.clues = nodes;
	}

	@Override
	public List<Clue> withdrawClues() {
		List<Clue> temp = clues;
		
		clues = null;
		
		return temp;
	}

}
