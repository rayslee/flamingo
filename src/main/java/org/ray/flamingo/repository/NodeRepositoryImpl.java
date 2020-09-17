package org.ray.flamingo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;
import org.ray.flamingo.rover.Rover;
import org.ray.flamingo.rover.RoverFactory;
import org.springframework.beans.factory.annotation.Autowired;

class NodeRepositoryImpl implements NodeRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RoverFactory rf;
	
	@Transactional
	@Override public void insertWithGivenId(long nodeId, String name) {
		em.createNativeQuery("insert into Node (id, name) values (:id, :name)")
		  .setParameter("id", nodeId)
		  .setParameter("name", name)
		  .executeUpdate();
	}
	
	@Override
	public boolean appendModule(long id, Module module) {
		return em.find(Node.class, id).getModules().add(module);
	}
	
	//ROVER METHODS
	
	private static final Predicate<Node> LIBRARY_MEASURE = nd -> nd.getModules().contains(Module.LIBRARY);
	private static final Predicate<Node> SCHEDULE_MEASURE = nd -> nd.getModules().contains(Module.SCHEDULE);
	
	@Override
	public List<Node> roverLibraries(long startNodeId) {
		return roverNodesByPredicate(startNodeId, LIBRARY_MEASURE);
	}
	
	@Override
	public List<Node> roverSchedulers(long startNodeId) {
		return roverNodesByPredicate(startNodeId, SCHEDULE_MEASURE);
	}
	
	@Override
	public List<Node> roverNodesWhoseNameLike(long startNodeId, String template) {
		Rover<Node> libraryFinder = rf.levelRandomTree(em.find(Node.class, startNodeId), 
												nd -> nd.getName().contains(template));
		
		Optional<Node> match = libraryFinder.next();
		List<Node> matches = new ArrayList<>();
		while(match.isPresent()) {
			matches.add(match.get());
			match = libraryFinder.next();
		}
		
		return matches;
	}
	
	//AUXILIARY METHODS
	
	private List<Node> roverNodesByPredicate(long startNodeId, Predicate<Node> measure) {
		Rover<Node> rover = rf.levelRandomTree(em.find(Node.class, startNodeId), measure);
		
		List<Node> nodes = new ArrayList<>();
		Optional<Node> node = rover.next();
		while(node.isPresent()) {
			nodes.add(node.get());
			node = rover.next();
		}
		
		return nodes;
	}
	
}
