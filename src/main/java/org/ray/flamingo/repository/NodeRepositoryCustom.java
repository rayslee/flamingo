package org.ray.flamingo.repository;

import java.util.List;

import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;

interface NodeRepositoryCustom {
	
	List<Node> roverLibraries(long startNodeId);

	List<Node> roverSchedulers(long startNodeId);
	
	List<Node> roverNodesWhoseNameLike(long startNodeId, String template);
	
	void insertWithGivenId(long nodeId, String name);

	boolean appendModule(long id, Module module);

}
