package simulator.cache;

import java.util.LinkedList;

import logger.Logger;

import simulator.packet.ContentPacket;
import simulator.packet.InterestPacket;
import statistic.Statistic;
import util.URI;

public class LRUCache extends Cache {

	public LRUCache(int cacheSize) {
		super(cacheSize);
	}
	public boolean add(ContentPacket cp)
	{
		 
		if(contains(cp.contentName))
		{
			//renew cache sequence
			int index = 0;
			for(CacheElement ce:cache)
			{
				if(ce.uri.equalsIgnoreCase(cp.contentName))
					break;
				index++;
			}
			Logger.log("Renew Cache hit" + cp.contentName + " @" + index, Logger.VERY_DETAIL);
			cache.offer(cache.remove(index));							//add at last(tail)
		}
		else
		{
			Logger.log("Renew Cache missed" + cp.contentName, Logger.VERY_DETAIL);
			uriMap.put(cp.contentName, cp.size);
			cache.offer(new CacheElement(cp.contentName,cp.size));		//add at last(head)
			left -= cp.size;
			while(left < 0)
			{
				CacheElement ce = cache.poll();							//remove from first
				uriMap.remove(ce.uri);
				left += ce.size;
			}
			//renew uriMap
		}
		return true;
	}
}
