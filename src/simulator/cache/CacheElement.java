package simulator.cache;

public class CacheElement {
	String uri;
	int size;
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
