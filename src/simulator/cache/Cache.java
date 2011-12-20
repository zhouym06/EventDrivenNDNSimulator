package simulator.cache;

public class Cache {
	CacheElement ce[];
	public Cache(int cacheSize) {
		ce = new CacheElement[cacheSize/1000];
	}
}
