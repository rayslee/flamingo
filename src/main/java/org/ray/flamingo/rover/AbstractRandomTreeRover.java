package org.ray.flamingo.rover;

import java.util.Optional;

import org.ray.flamingo.barn.RandomTree;

abstract class AbstractRandomTreeRover<T extends RandomTree<T>> implements RandomTreeRover<T> {
	
	protected Optional<T> getFirstChild(T parent) {
		return parent
					.getChilds()
					.stream()
					.min((c1, c2) -> c1.compareTo(c2));
	}
	
	protected Optional<T> electNextSibling(T parent, T junior) {
		return parent
					.getChilds()
					.stream()
					.filter(child -> child.compareTo(junior) > 0)		//find untested siblings
					.min((c1, c2) -> c1.compareTo(c2));				//return the littlest one
	}

}
