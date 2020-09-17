package org.ray.flamingo.pollution;

interface Guarder<T> {
	
	Guarder<T> enBlacklist(T bad);
	
	boolean isBlocked(T candidate);

}