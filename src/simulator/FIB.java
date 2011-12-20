package simulator;

import simulator.packet.InterestPacket;
import topology.Router;

public class FIB {								//Forward Information Base
	FIBEntry[] entries = null;
	public Router getNextRouter(String uri)
	{
		int longestFit = 0;
		Router nextRouter = null;
		for(FIBEntry e:entries)
		{
			if(e.fitLength(uri) > longestFit)
				nextRouter = e.nextRouter;
		}
		return nextRouter;
	}
	public void announce(String prefix, Router from)
	{
		//to do
	}
	public void handle(InterestPacket packet) {
		getNextRouter(packet.contentName);
		
	}
}
