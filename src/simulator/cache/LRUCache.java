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
	public boolean handle(ContentPacket cp)
	{
		 
		if(contains(cp.contentName))
		{
			System.out.println("contains");
			//renew cache sequence
			/**/
			int index = 0;
			for(CacheElement ce:cache)
			{
				if(ce.uri.equalsIgnoreCase(cp.contentName))
					break;
				index++;
			}
			Logger.log("Renew Cache hit" + cp.contentName + " @" + index, Logger.VERY_DETAIL);
			cache.offer(cache.remove(index));							//add at last(tail)
			/*
			uriMap.put(cp.contentName, cp.size);
			left -= cp.size;
			*/
			return true;
		}
		else
		{
			//System.out.println("!!! not contains");
			//if(cp.timeLived % 2 == 1)
			{
				Logger.log("Renew Cache missed" + cp.contentName, Logger.VERY_DETAIL);
				uriMap.put(cp.contentName, cp.size);
				cache.offer(new CacheElement(cp.contentName,cp.size));		//add at last(tail)
				left -= cp.size;
				//System.out.println("left" + left);
				while(left < 0)
				{
					CacheElement ce = cache.poll();							//remove from first
					uriMap.remove(ce.uri);
					//System.out.println("before" + left);
					left += ce.size;
					//System.out.println("remove" + ce.uri);
					//System.out.println("after" + left);
					
				}
			}
			//renew uriMap
			return false;
		}
		
	}
}
