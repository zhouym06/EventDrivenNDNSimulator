package event;

import logger.Logger;
import simulator.packet.ContentPacket;
import topology.Router;

public class ContentTask extends Task {
	public ContentPacket cPacket;
	public Router target;
	
	public ContentTask(ContentPacket cPacket, Router target, double time, TimeLine timeline)
	{
		super(time, timeline);
		this.cPacket = cPacket;
		this.target = target;
	}
	public void execute()
	{
		target.handle(this);
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time) + cPacket.contentName + String.valueOf(target.routerID), Logger.INFO);
	}
}
