package org.ray.flamingo.service;

import java.util.ArrayList;
import java.util.Optional;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.index.Channel;
import org.ray.flamingo.index.Indexable;
import org.ray.flamingo.index.NodeChannel;
import org.ray.flamingo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NodeService implements Indexable {
	
	@Autowired
	private NodeRepository nr;
	
	//PUBLIC METHODS
	
	public Optional<Node> find(Long id) {
		return nr.findById(id);
	}
	
	public void remove(Long id) {
		if(!nr.existsById(id))
			return;
		
		nr.deleteById(id);
		
		log.info("Node [id=" + id + " is removed.]");
	}
	
	public void appendAttribute(Long id, String key, String val) {
		Optional<Node> nd = find(id);
		boolean added = false;
		
		if(nd.isPresent())
			added = nd.get().appendAttribute(examine(key), examine(val));
		
		if(added) {
			nr.save(nd.get());
			log.info(nd.get() + " appended new attribute [" + key + " : " + val + "]");
		}
	}
	
	public void appendChild(Long parentId, String childName) {
		Optional<Node> nd = find(parentId);
		boolean added = false;
		
		if(nd.isPresent()) {
			Node child = new Node();
			child.setName(examine(childName));
			child.setParent(nd.get());
			added = nd.get().appendChild(child);
		}
		
		if(added) {
			log.info(nd.get() + " appended new child names \"" + childName + "\"");
			nr.save(nd.get());
		}
		
	}
	
	//Methods of Indexable
	
	@Override
	public Channel buildConnection(long startPos, String message) {
		return new NodeChannel(
							new ArrayList<>(
									nr.roverNodesWhoseNameLike(startPos, message)));
	}

	@Override
	public Channel stuffClues(Channel channel, int floorSize) {
		return channel;
	}
	
	//Anti pollution
	
	@Autowired private PollutionService pollServ;
	
	private String examine(String candidate) {
		return pollServ.replaceBadWords(candidate);
	}

}
