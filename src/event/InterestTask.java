package event;

import simulator.packet.InterestPacket;
import topology.Router;

public class InterestTask extends Task {
	public InterestPacket iPacket;
	public Router target;
	public Router from;
	public InterestTask(String uri, Router target,  Router from, double time, TimeLine timeline)
	{
		super(time, timeline);
		this.iPacket = new InterestPacket(uri);
		this.target = target;
		this.from = from;
	}
	public void execute()
	{
		target.handle(this, from);
	}
}
