package org.ray.flamingo.index;

import lombok.Data;

@Data
public class Source {
	
	private Indexable indexable;
	
	private Channel channel;
	
	public Source(Indexable indexable, Channel channel) {
		this.indexable = indexable;
		this.channel = channel;
	}

}
