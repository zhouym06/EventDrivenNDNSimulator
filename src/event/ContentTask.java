package event;

import logger.Logger;
import simulator.packet.ContentPacket;
import topology.Router;

public class ContentTask extends Task {
	public ContentPacket cPacket;
	public Router target;
	
	public ContentTask(ContentPacket cPacket, Router target, double time)
	{
		super(time);
		this.cPacket = cPacket;
		this.target = target;
	}
	public void execute()
	{
		Logger.log(toString() + " execute()", Logger.DEBUG);
		target.handle(this);
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time) + cPacket.contentName + String.valueOf(target.routerID), Logger.INFO);
	}
	public String toString()
	{
		return time + "\tContentTask(" + cPacket.contentName + ") at router" + target.routerID;
	}
}
