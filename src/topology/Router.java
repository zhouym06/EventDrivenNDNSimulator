package topology;


import java.util.*;

import logger.Logger;

import event.ContentTask;
import event.InterestTask;
import event.TimeLine;

import simulator.FIB;
import simulator.PIT;
import simulator.cache.Cache;
import simulator.cache.LRUCache;
import simulator.packet.*;
import statistic.Statistic;
import util.URI;


public class Router {
	public int level;
	public int routerID;
	//public int interfaceNum;						//first interfaceNum interfaces are between routers
	public ArrayList<Edge> interfaces = null;
	
	PIT pit;
	FIB fib;
	Cache cache;
	
	public Router(int routerID, int cacheSize){
		this.routerID = routerID;
		pit = new PIT();
		fib = new FIB();
		this.cache = new LRUCache(cacheSize);
		this.interfaces = new ArrayList<Edge>();
	}
	public void handle(InterestTask iTask, Router from)	
	{
		Logger.log("Router" + routerID + ":handleInterest(" + iTask.iPacket.contentName+ ") from router" + from.routerID + "at router" + this.routerID, Logger.ROUTER);
		iTask.iPacket.timeLived++;
		if(cache.contains(iTask.iPacket.contentName))
		{
			Statistic.countHit(URI.getContentNo(iTask.iPacket.contentName));
			ContentTask ct = new ContentTask(cache.getContent(iTask.iPacket.contentName), from, iTask.getTime() + pit.getFowardTime());
			TimeLine.add(ct);
			return;
		}

		Statistic.countMiss(URI.getContentNo(iTask.iPacket.contentName));
		if(pit.addPI(iTask.iPacket, from))
		{
			int i = fib.getNextInterface(iTask.iPacket.contentName);
			double time = iTask.getTime() + fib.getLookupTime();
			//double time = iTask.getTime();
			TimeLine.add(new InterestTask(iTask.iPacket.contentName, interfaces.get(i).theOther(this),  this, time));
		}
	}
	public void handle(AnnoucePacket aPacket, Edge fromInterface, double time)	
	{
		aPacket.timeLived++;
		int index = interfaces.indexOf(fromInterface);
		Logger.log("Router" + routerID + ":handleAnnouce(" + aPacket.contentName + "): at router" + this.routerID + " from edge" + fromInterface.edgeID + "(index in array " + index + ")", Logger.ROUTER);
		if(fib.announce(aPacket, index, time))
		{
			for(Edge e:interfaces)
			{
				if(!e.equals(fromInterface))
				{
					
					e.theOther(this).handle(new AnnoucePacket(aPacket), e, time + e.delay);
				}
			}
		}
		Logger.log("Router:handleAnnouce fin", Logger.ROUTER);
	}
	//≤ª∑÷segment¡À
	public void handle(ContentTask cTask)
	{
		Logger.log("Router" + routerID + ":handleContent " + cTask.cPacket.contentName, Logger.ROUTER);
		//cTask.cPacket.timeLived = cTask.cPacket.timeLived++;		// why doesn't this work?
		cache.handle(cTask.cPacket);	// renew cache
		ArrayList<Router> rts = pit.handle(cTask.cPacket);
		for(Router r:rts)
		{
			Statistic.addTTL(URI.getContentNo(cTask.cPacket.contentName), 1);
			Logger.log("Router" + routerID + ":handleContent(" + cTask.cPacket.contentName+ ")" + " ttl = " + cTask.cPacket.timeLived + " from this router" + this.routerID + "to router" + r.routerID, Logger.ROUTER);
			ContentTask ct = new ContentTask(new ContentPacket(cTask.cPacket), r, cTask.getTime() + pit.getFowardTime());
			//ContentTask ct = new ContentTask(new ContentPacket(cTask.cPacket), r, cTask.getTime());
			TimeLine.add(ct);
		}
		
	}
	public void displayFIB() {
		Logger.log("FIBEntry of " + routerID + ":", Logger.DEBUG);
		fib.display();
		int i = fib.getNextInterface("Server0");
		Logger.log("\tinterfaces" + i + "routerID" + interfaces.get(i).theOther(this).routerID, Logger.DEBUG);
	}
	public void display() {
		Logger.log("Router" + routerID + ":" + " has " + interfaces.size() + " edges", Logger.INFO);
		for(Edge e: interfaces)
		{
			e.display();
		}
	}
	public String toString()
	{
		return "Router" + routerID + ":";
	}
}
