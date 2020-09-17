package org.ray.flamingo.rover;

import java.util.function.Predicate;

import org.ray.flamingo.barn.RandomTree;
import org.springframework.stereotype.Component;

@Component
public class RoverFactory {
	
	public <T extends RandomTree<T>> Rover<T> levelRandomTree(T start, Predicate<T> measure) {
		
		return new LevelRandomTreeRover<T>(start, measure);
	}
	
	public <T extends RandomTree<T>> Rover<T> preorderRandomTree(T start, Predicate<T> measure) {
		
		return new PreorderRandomTreeRover<T>(start, measure);
	}
	
	public <T extends RandomTree<T>> Rover<T> postorderRandomTree(T start, Predicate<T> measure) {
		
		return new PostorderRandomTreeRover<T>(start, measure);
	}

}
