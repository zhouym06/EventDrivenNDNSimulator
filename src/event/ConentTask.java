package event;

import logger.Logger;
import simulator.packet.ContentPacket;
import topology.Router;

public class ConentTask extends Task {
	ContentPacket p;
	Router target;
	public void execute()
	{
		target.handle(p);
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time) + p.contentName + String.valueOf(target.routerID), 3);
		
	}
}
