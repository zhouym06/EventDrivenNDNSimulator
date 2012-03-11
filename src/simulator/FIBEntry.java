package simulator;


public class FIBEntry {
	int distance = Integer.MAX_VALUE;
	double delay = Double.MAX_VALUE;
	String prefix = null;
	int nextInterface = -1;
	public FIBEntry(String prefix, int nextInterface, int distance, double delay)
	{
		this.prefix = prefix;
		this.nextInterface = nextInterface;
		this.distance = distance;
		this.delay = delay;
	}
	public int fitLength(String uri)				//Longest prefix match
	{
		int l = 0;
		if(uri == null || prefix == null)
			return 0;
		int l1 = prefix.length();
		int l2 = uri.length();
		if(l1 > l2)
			return 0;
		while(l < l1 && l <l2)
		{
			if(uri.charAt(l) == prefix.charAt(l))
				l++;
			else
				return 0;							//have different char
		}
		return l;
	}
	public int getNextInterface()
	{
		return nextInterface;
	}
	public void setNextInterface(int nextInterface)
	{
		this.nextInterface = nextInterface; 
	}
	public void setDistance(int distance)
	{
		this.distance = distance; 
	}
	public void setDelay(double delay)
	{
		this.delay = delay;
	}
}
