package topology;


import java.util.*;

import simulator.FIB;
import simulator.PIT;
import simulator.cache.Cache;
import simulator.packet.*;


public class Router {
	public int level;
	public int routerID;
	public int interfaceNum;
	public ArrayList<Edge> interfaces = null;
	
	PIT pit;
	FIB fib;
	Cache cache;
	
	public Router(){}
	public Router(int cacheSize)
	{
		cache = new Cache(cacheSize);
	}
	public void announce(String prefix, Router from)
	{
		fib.announce(prefix, from);
	}
	public void handle(InterestPacket iPacket, Router from)	
	{
		iPacket.timeLived++;
		pit.addPI(iPacket, from);
		fib.handle(iPacket);
		
	}
	public void handle(AnnoucePacket aPacket, Router from)	
	{
		aPacket.timeLived++;
		
		
	}
	public void handle(ContentPacket dPacket)		//大于1k者，先发送uri.01 uri.02 ...最后发送uri以清除PIT
	{
		dPacket.timeLived++;
	}
}
