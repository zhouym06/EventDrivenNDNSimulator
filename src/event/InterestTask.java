package event;

import simulator.packet.InterestPacket;
import topology.Router;

public class InterestTask extends Task {
	InterestPacket p;
	Router target;
	Router from;
	public void execute()
	{
		target.handle(p, from);
	}
}
