package org.ray.flamingo.service;

import org.ray.flamingo.barn.Module;
import org.ray.flamingo.barn.Node;
import org.ray.flamingo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class ModuleService {
	
	@Autowired
	private NodeRepository nr;
	
	void register(Long id, Module module) {
		Node node = nr.findById(id).get();
		
		boolean added = nr.appendModule(id, module);
		
		if(added) {
			nr.save(node);
			log.info(node + " registered new module \"" + module + "\"");
		}	
	}
	
//	void registerTillRoot(Long id, Module module) {
//		Node child  = nr.findById(id).get();
//		
//		do {
//			register(child.getId(), module);
//			
//			child = child.getParent();
//		} 
//		
//		while(child != null);
//	}

}
