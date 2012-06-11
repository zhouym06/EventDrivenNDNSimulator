package simulator.cache;

public class CacheElement {
	String uri;
	int size;
	// TO DO:
	int frequence;
	int ttl;
	public CacheElement(String uri, int size)
	{
		this.uri = uri;
		this.size = size;
		
	}
	public boolean equals(Object o)
	{
		return this.uri.equalsIgnoreCase(((CacheElement)o).uri);
	}
}
