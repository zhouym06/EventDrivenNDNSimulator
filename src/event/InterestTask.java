package event;

import logger.Logger;
import simulator.packet.InterestPacket;
import topology.Router;

public class InterestTask extends Task {
	public InterestPacket iPacket;
	public Router target;
	public Router from;
	public InterestTask(String uri, Router target,  Router from, double time)
	{
		super(time);
		this.iPacket = new InterestPacket(uri);
		this.target = target;
		this.from = from;
	}
	public void execute()
	{
		Logger.log("\tTask:" + toString() + " executing", Logger.DEBUG);
		target.handle(this, from);
	}
	public String toString()
	{
		return time + "\tInterestTask(" + iPacket.contentName + ") from router" + from.routerID + "\tto router" + target.routerID;
	}
}
