package simulator.cache;

import logger.Logger;
import simulator.packet.ContentPacket;

public class LFUCache extends Cache {
	public LFUCache(int cacheSize) {
		super(cacheSize);
	}

	@Override
	public boolean handle(ContentPacket cp) {
		if(contains(cp.contentName))
		{
			
		}
		else
		{
			
		}
		return true;
	}
}
