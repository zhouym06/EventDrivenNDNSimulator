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
import util.MyRandom;


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
		Logger.log("Router:handleInterest(" + iTask.iPacket.contentName+ ") from router" + from.routerID + "at router" + this.routerID, Logger.DEBUG);
		iTask.iPacket.timeLived++;
		pit.addPI(iTask.iPacket, from);
		int i = fib.getNextInterface(iTask.iPacket.contentName);
		double time = iTask.getTime() + fib.getSize() * 0.01; 				//* MyRandom.nextPoisson(10) / 10;
		iTask.getTimeLine().add(new InterestTask(iTask.iPacket.contentName, interfaces.get(i).theOther(this),  this, time, iTask.getTimeLine()));
	}
	public void handle(AnnoucePacket aPacket, Edge fromInterface, double time)	
	{
		aPacket.timeLived++;
		int index = interfaces.indexOf(fromInterface);
		Logger.log("Router:handleAnnouce(" + aPacket.contentName + "): at router" + this.routerID + " from edge" + fromInterface.edgeID + "(index in array " + index + ")", Logger.DEBUG);
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
		Logger.log("Router:handleAnnouce fin", Logger.DEBUG);
	}
	public void handle(ContentTask cTask)		//大于1k者，先发送uri.01 uri.02 ...最后发送uri以清除PIT
	{
		cTask.cPacket.timeLived++;
		//cache.handle();
	}
	public void displayFIB() {
		Logger.log("FIBEntry of " + routerID + ":", Logger.INFO);
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
}
