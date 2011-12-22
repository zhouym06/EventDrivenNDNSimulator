package topology;


import java.util.*;

import logger.Logger;

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
	public int interfaceNum;						//first interfaceNum interfaces are between routers
	public ArrayList<Edge> interfaces = null;
	
	PIT pit;
	FIB fib;
	Cache cache;
	
	public Router(int routerID, int cacheSize){
		this.routerID = routerID;
		pit = new PIT();
		fib = new FIB();
		this.cache = new Cache(cacheSize);
		this.interfaces = new ArrayList<Edge>();
	}
	public void handle(InterestTask iTask, Router from)	
	{
		iTask.iPacket.timeLived++;
		pit.addPI(iTask.iPacket, from);
		fib.handle(iTask.iPacket);
		//to do
		
	}
	public void handle(AnnoucePacket aPacket, Edge fromInterface, double time)	
	{
		aPacket.timeLived++;
		int index = interfaces.indexOf(fromInterface);
		if(fib.announce(aPacket, index, time))
		{
			for(Edge e:interfaces)
				Logger.log("\t" + aPacket.contentName + "routerID" + this.routerID + "edgeID" + e.edgeID, Logger.DEBUG);
			for(Edge e:interfaces)
			{
				if(!e.equals(fromInterface))
					e.theOther(this).handle(aPacket, e, e.delay);
			}
		}
	}
	public void handle(ContentTask cTask)		//大于1k者，先发送uri.01 uri.02 ...最后发送uri以清除PIT
	{
		cTask.cPacket.timeLived++;
	}
}
