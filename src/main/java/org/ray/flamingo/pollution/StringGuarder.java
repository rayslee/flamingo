package org.ray.flamingo.pollution;

interface StringGuarder extends Guarder<String> {
	
	@Override Guarder<String> enBlacklist(String bad);
	
	@Override boolean isBlocked(String word);
	
	/**
	 * start validate a sentence from fixed position.
	 * 
	 * return the position of matched or -1.
	 * 
	 * @param sentence
	 * @param startPos
	 * @return
	 */
	int isBlocked(String sentence, int startPos);

}