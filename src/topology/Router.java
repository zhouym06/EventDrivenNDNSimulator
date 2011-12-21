package topology;


import java.util.*;

import event.ContentTask;
import event.InterestTask;
import event.TimeLine;

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
	
	public Router(int routerID, int cacheSize){
		this.routerID = routerID;
		cache = new Cache(cacheSize);
	}
	public void announce(String prefix, Router from)
	{
		fib.announce(prefix, from);
	}
	public void handle(InterestTask iTask, Router from)	
	{
		iTask.iPacket.timeLived++;
		pit.addPI(iTask.iPacket, from);
		fib.handle(iTask.iPacket);
		//to do
		
	}
	public void handle(AnnoucePacket aPacket, Router from, double time)	
	{
		aPacket.timeLived++;
		
		
	}
	public void handle(ContentTask cTask)		//大于1k者，先发送uri.01 uri.02 ...最后发送uri以清除PIT
	{
		cTask.cPacket.timeLived++;
	}
}
