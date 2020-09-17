package org.ray.flamingo.barn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.ray.flamingo.index.Clue;
import org.ray.flamingo.library.BookLink;
import org.ray.flamingo.schedule.Schedule;

/**
 * A <em>navigable-barn</em> acts like tree.
 * 
 * <p>
 * Navigable:
 * every node has one link to its parent and many links to its child(s)
 * which makes it behave like a tree.For better supporting traversal 
 * operations, this class also defined the accessing order on child(s).
 * 
 * <p>
 * Barn: 
 * the primary responsibility of node is storing data but it won't rule
 * what kind of types the data should be.That is to say, node only cares
 * about storing and fetching, not managing or processing.
 *
 * @author Ray LEE
 * @since 1.0
 */
@Entity
@lombok.Data
public class Node implements RandomTree<Node>, Clue {
	
	//Persistence management
	
	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	private Long id;
	
	@NotNull
	private String name;
	
	@ManyToOne
	private Node parent;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private List<Node> childs = new ArrayList<>();
	
	@ElementCollection
	@MapKeyColumn(length = 50)
	private Map<String, String> attributes = new HashMap<>();
	
	//Public methods
	
	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + "]";
	}
	
	public boolean appendAttribute(String key, String val) {
		if(isBlank(key) || isBlank(val))
			return false;
		
		attributes.put(key, val);
		
		return true;
	}
	
	public boolean appendChild(Node newbie) {
		if(isBlank(newbie.name))
			return false;
		
		for(Node child : childs)
			if(child.name.equals(newbie.name))
				return false;
		
		return childs.add(newbie);
	}
	
	private boolean isBlank(String s) {
		return s.trim().equals("");
	}
	
	//AS RandomTree
	
	@Override
	public Node getParent() {
		return parent;
	}
	
	@Override
	public List<Node> getChilds() {
		return childs;
	}
	
	@Override 
	public int compareTo(Node other) {
		return this.id.compareTo(other.id);
	}
	
	//AS Clue
	
	@Override
	public String getContent() {
		return toString();
	}
	
	//Data mounting
	
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<Module> modules = new TreeSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "depot")
	private List<Schedule> schedules = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "depot")
	private Set<BookLink> bookLinks = new HashSet<>();
	
}
