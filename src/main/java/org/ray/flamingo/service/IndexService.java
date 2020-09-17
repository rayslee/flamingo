package org.ray.flamingo.service;

import java.util.ArrayList;
import java.util.List;

import org.ray.flamingo.PageProps;
import org.ray.flamingo.index.Broker;
import org.ray.flamingo.index.Channel;
import org.ray.flamingo.index.Clue;
import org.ray.flamingo.index.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
	
	//EVERY TIME U added an indexable service, u should change this method accordingly
	
	@Autowired private LibraryService libServ;
	
	@Autowired private NodeService nodServ;
	
	@Autowired private ScheduleService schServ;
	
	private void exploreSources(Broker broker) {
		List<Source> sources = new ArrayList<>();
		
		Channel libChan = libServ.buildConnection(broker.getNodeId(), broker.getMessage());
		Channel nodChan = nodServ.buildConnection(broker.getNodeId(), broker.getMessage());
		Channel schChan = schServ.buildConnection(broker.getNodeId(), broker.getMessage());
		sources.add(new Source(libServ, libChan));
		sources.add(new Source(nodServ, nodChan));
		sources.add(new Source(schServ, schChan));
		
		broker.setSources(sources);
	}
	
	//PUBLIC METHODS
	
	@Autowired
	private PageProps pageProps;
	
	public Broker findBroker(long nodeId, String message) {
		return new Broker(nodeId, message);
	}
	
	public Broker askForMoreMessage(Broker broker) {
		if(sourcesUninitialized(broker))
			exploreSources(broker);
		
		while(runOutOfCachedData(broker) && sourcesAvailable(broker)) {
			List<Source> sources = broker.getSources();
			Source source = sources.get(sources.size() - 1);
			List<Clue> news = source.getIndexable()
							/* Indexable */.stuffClues(source.getChannel(), pageProps.getSize())
									/* Channel */.withdrawClues();
			
			//no more clues, remove candidate from sources
			if(news.size() < pageProps.getSize())
				sources.remove(sources.size() - 1);
			
			broker.getCache().addAll(news);
		}
		
		setResultsOfNextPage(broker);
		return broker;
	}
	
	//AUXILIARY METHODS
	
	private boolean sourcesUninitialized(Broker broker) {
		return broker.getSources() == null;
	}
	
	//unused cached items enough for next page ?
	private boolean runOutOfCachedData(Broker broker) {
		return broker.getCachePos() + pageProps.getSize() > broker.getCache().size();
	}
	
	//there is any source in broker ?
	private boolean sourcesAvailable(Broker broker) {
		return broker.getSources().size() > 0;
	}
	
	//hang on the last page if no more items available
	private void setResultsOfNextPage(Broker broker) {
		if(isLastPage(broker))
			return;
		
		List<Clue> cache = broker.getCache();
		int beginPos = broker.getCachePos();
		int endPos   = computeEndPos(beginPos, pageProps.getSize(), cache.size());
		
		/* When i try to set results as a sublist of cache, exception thrown
		 * 	 !Caused by: java.util.ConcurrentModificationException 
		 * 	 !at java.util.ArrayList$SubList.checkForComodification(ArrayList.java:1231)
		 * I couldn't figure out why so i just create a new object instead.
		 */
		broker.setResults(new ArrayList<>(cache.subList(beginPos, endPos)));
		broker.setCachePos(endPos);
	}
	
	private boolean isLastPage(Broker broker) {
		return broker.getCachePos() == broker.getCache().size();
	}
	
	private int computeEndPos(int beginPos, int pageSiz, int totalSiz) {
		return beginPos + pageSiz > totalSiz ? totalSiz : beginPos + pageSiz;
	}
	
}
