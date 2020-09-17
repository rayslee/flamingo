package org.ray.flamingo.index;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Broker {
	
	//PART ONE
	
	private long nodeId;
	
	private String message;
	
	public Broker(long nodeId, String message) {
		this.nodeId = nodeId;
		this.message = message;
	}
	
	//PART TWO
	
	private List<Source> sources;
	
	private List<Clue> cache = new ArrayList<>();
	
	private int cachePos;
	
	private List<Clue> results;		//sub list of cache(part of cache)
	
	public List<Clue> getResults() {
		return results;
	}

}
