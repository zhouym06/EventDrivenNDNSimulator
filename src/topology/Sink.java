package topology;

import java.util.ArrayList;

import simulator.FIB;
import simulator.PIT;
import simulator.cache.LRUCache;
import simulator.packet.ContentPacket;
import statistic.Statistic;
import util.URI;
import event.ContentTask;
import event.TimeLine;
import logger.Logger;

public class Sink extends Router {
	Router linkedTo;
	public Sink(Router linkedTo, int sinkNo)
	{
		super(sinkNo, 0);
		this.linkedTo = linkedTo;
	}
	
	public void  display()
	{
		Logger.log("Sink" + routerID + " has " + interfaces.size() + " edges", Logger.INFO);
		for(Edge e: interfaces)
		{
			e.display();
		}
	}
	
	public void handle(ContentTask cTask)
	{
		//Statistic.addTTL(URI.getContentNo(cTask.cPacket.contentName), 1);
		//Statistic.addTTL(URI.getContentNo(cTask.cPacket.contentName), cTask.cPacket.timeLived);
		Logger.log("Sink:handleContent " + URI.getContentNo(cTask.cPacket.contentName) + "TTL:" + cTask.cPacket.timeLived, Logger.ROUTER);
	}

}
