package org.ray.flamingo.rover;

import java.util.Optional;

public interface Rover<T> {
	
	Optional<T> next();
	
//	List<T> next(int num);
	
//	Optional<T> previous();
	
//	List<T> previous(int num);
	
}

