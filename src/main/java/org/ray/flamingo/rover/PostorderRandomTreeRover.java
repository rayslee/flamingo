package org.ray.flamingo.rover;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import org.ray.flamingo.barn.RandomTree;

class PostorderRandomTreeRover<T extends RandomTree<T>> extends AbstractRandomTreeRover<T> {
	
	private Deque<T> memory = new LinkedList<>();
	
	private Predicate<T> measure;
	
	public PostorderRandomTreeRover(T start, Predicate<T> measure) {
		enqueueFirstChildTillBottom(memory, start);
		this.measure = measure;
	}
	
	@Override
	public Optional<T> next() {
		final T candidate = memory.pollLast();
		if(candidate == null)
			return Optional.empty();
		
		enqueueNextSiblingIfExist(memory, candidate);
		
		if(measure.test(candidate))
			return Optional.of(candidate);
		return next();
	}
	
	private void enqueueNextSiblingIfExist(Deque<T> memory, T junior) {
		final T parent = memory.peekLast();
		if(parent == null)
			return;
		
		Optional<T> senior = super.electNextSibling(parent, junior);
		
		if(senior.isPresent())
			enqueueFirstChildTillBottom(memory, senior.get());
	}
	
	private void enqueueFirstChildTillBottom(Deque<T> memory, T parent) {
		memory.addLast(parent);
		
		Optional<T> child = super.getFirstChild(parent);
		
		while(child.isPresent()) {
			memory.addLast(child.get());
			parent = child.get();
			child = getFirstChild(parent);
		}
	}
	
}
