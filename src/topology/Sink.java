package topology;

import java.util.ArrayList;

import simulator.packet.ContentPacket;
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
		Logger.log("Sink:handleContent" + cTask.cPacket.contentName, Logger.DEBUG);
	}

}
