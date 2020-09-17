package org.ray.flamingo.rover;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import org.ray.flamingo.barn.RandomTree;

class PreorderRandomTreeRover<T extends RandomTree<T>> extends AbstractRandomTreeRover<T> {
	
	private Deque<T> memory = new LinkedList<>();
	
	private Predicate<T> measure;
	
	public PreorderRandomTreeRover(T start, Predicate<T> measure) {
		this.memory.addLast(start);
		this.measure = measure;
	}

	@Override
	public Optional<T> next() {
		final T candidate = memory.peekLast();
		if(candidate == null)
			return Optional.empty();
		
		enqueueNextCandidate(memory);
		if(measure.test(candidate))
			return Optional.of(candidate);
		
		return next();
	}
	
	//if child presents, enqueue child, otherwise enqueue next sibling
	private void enqueueNextCandidate(Deque<T> memory) {
		//assert(memory.isEmpty == false)
		final Optional<T> child = super.getFirstChild(memory.getLast());
		
		if(child.isPresent()) {
			memory.addLast(child.get());
			return;
		}
		
		enqueueNextSibling(memory);
	}
	
	//continue checking the existence of next sibling
	private void enqueueNextSibling(Deque<T> memory) {
		//assert(memory.isEmpty == false)
		T junior = memory.removeLast();
		T parent = memory.peekLast();
		if(parent == null)
			return;
		
		Optional<T> senior = super.electNextSibling(parent, junior);
		while(!senior.isPresent()) {
			junior = memory.removeLast();
			parent = memory.peekLast();
			
			if(parent == null)
				return;
			
			senior = super.electNextSibling(parent, junior);
		}
		
		memory.addLast(senior.get());
	}

}
