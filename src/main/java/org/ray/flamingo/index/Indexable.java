package org.ray.flamingo.index;

public interface Indexable {
	
	Channel buildConnection(long startPos, String message);
	
	Channel stuffClues(Channel channel, int floorSize);

}
