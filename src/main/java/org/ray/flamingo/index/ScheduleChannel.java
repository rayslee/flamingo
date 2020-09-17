package org.ray.flamingo.index;

import java.util.ArrayList;
import java.util.List;

import org.ray.flamingo.barn.Node;

import lombok.Data;

@Data
public class ScheduleChannel implements Channel {
	
	//PART ONE
	
	private List<Node> schedulers;
	
	private String indexValue;
	
	public ScheduleChannel(List<Node> schedulers, String indexValue) {
		this.schedulers = schedulers;
		this.indexValue = indexValue;
	}
	
	//PART TWO
	
	private List<Clue> clues = new ArrayList<>();

	@Override
	public List<Clue> withdrawClues() {
		List<Clue> temp = clues;
		
		clues = new ArrayList<>();
		
		return temp;
	}

}
