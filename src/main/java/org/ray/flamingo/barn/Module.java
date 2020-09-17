package org.ray.flamingo.barn;

public enum Module {
	//indicate node has schedule management function
	SCHEDULE, 
	
	//indicate node has mass quantity of books
	LIBRARY;
	
	public String getLowerCase() {
		return this.name().toLowerCase();
	}
}
