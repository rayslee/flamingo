package org.ray.flamingo.index;

import java.util.List;

public interface Channel {
	
	/*
	 * Normally, channel will clear up its clues cache after withdrawing.
	 */
	List<Clue> withdrawClues();

}
