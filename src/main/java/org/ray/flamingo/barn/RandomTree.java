package org.ray.flamingo.barn;

import java.util.List;

/**
 * A tree with only one parent but any number of child(s).
 * 
 * <p>
 * Note: this interface extends another interface {@link Comparable} for 
 * better supporting traversal operations, which indicates the accessing 
 * order on child(s) through the comparing result.
 *
 * @author Ray LEE
 * @since 1.0
 */
public interface RandomTree<T> extends Comparable<T> {
	
	/**
	 * Get the parent of tree.
	 * 
	 * @return parent
	 */
	T getParent();
	
	/**
	 * Get the child(s) of tree.
	 * 
	 * @return child(s)
	 */
	List<T> getChilds();
	
	/**
	 * Compares this tree with the specified object for order.Returns a
     * negative integer, zero, or a positive integer as this tree should
     * be accessed later, meanwhile or earlier than the specified object.
	 * 
	 * @param o the object to be compared.
	 * @return Returns a negative integer, zero, or a positive integer as 
	 * 		   this tree should be accessed later, meanwhile or earlier 
	 * 		   than the specified object.
	 */
	@Override int compareTo(T o);
	
}
