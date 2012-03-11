package simulator.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import simulator.packet.ContentPacket;
import simulator.packet.InterestPacket;

public abstract class Cache {
	int left = -1;
	LinkedList<CacheElement> cache;  
	HashMap<String, Integer> uriMap; //<uri, size>.  <uri,index> may cost more computation though more straight forward 
	public Cache(int cacheSize) {
		cache = new LinkedList<CacheElement>();
		uriMap = new HashMap<String, Integer>(); 
		left = cacheSize;
	}
	
	public boolean contains(String ContentName)
	{
		return uriMap.containsKey(ContentName);
	}
	public ContentPacket getContent(String ContentName)
	{
		int size = uriMap.get(ContentName);
		return new ContentPacket(ContentName, size);
	}
	public abstract boolean add(ContentPacket cp);

	public void handle(ContentPacket cPacket) {
		add(cPacket);
	}
}
