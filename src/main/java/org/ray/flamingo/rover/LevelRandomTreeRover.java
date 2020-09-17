package org.ray.flamingo.rover;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;

import org.ray.flamingo.barn.RandomTree;

class LevelRandomTreeRover<T extends RandomTree<T>> implements RandomTreeRover<T> {
	
	private Queue<T> memory = new LinkedList<>();
	
	private Predicate<T> measure;
	
	public LevelRandomTreeRover(T start, Predicate<T> measure) {
		this.memory.add(start);
		this.measure = measure;
	}
	
	@Override
	public Optional<T> next() {
		final T candidate = memory.poll();
		if(candidate == null)
			return Optional.empty();
		
		for(T child : candidate.getChilds())
			memory.add(child);
		
		if(measure.test(candidate))
			return Optional.of(candidate);
		return next();
	}

}
