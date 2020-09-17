package org.ray.flamingo.pollution;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Umbrella implements StringGuarder, Serializable {

	private static final long serialVersionUID = 3689179878264096888L;
	
	private Map<Character, Object> blacklist = new HashMap<>();
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Umbrella enBlacklist(String bad) {
		Map temp = blacklist;
		
		for(char c : bad.toCharArray()) {
			if(temp.get(c) == null)
				temp.put(c, new HashMap<Character, Object>());
			
			temp = (Map) temp.get(c);
		}
		
		return this;
	}
	
	@Override
	public boolean isBlocked(String word) {
		return isBlocked(word, 0) != -1;
	}
	
	/**
	 * If sentence starts with the word in blacklist,
	 * Return the end position of the matched word;
	 * Otherwise return -1.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public int isBlocked(String sentence, int startPos) {
		Map temp = (Map) blacklist.get(sentence.charAt(startPos));
		
		//U can code better HERE.
		if(temp != null)
			for(char c : sentence.substring(++startPos).toCharArray()) {
				if((temp = (Map) temp.get(c)) == null)	//lost matching
					return -1;
				if(temp.isEmpty()) 						//matched till end, bingo.
					return startPos;
				startPos++;
			}
		
		return -1;
	}

}








