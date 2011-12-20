package simulator;

import java.util.HashMap;
import java.util.ArrayList;

import simulator.packet.InterestPacket;
import topology.Edge;
import topology.Router;

public class PIT {
	HashMap<String, ArrayList<Router>> pendingInterest = null;
	public PIT()
	{
		pendingInterest = new HashMap<String, ArrayList<Router>>();
	}
	public void addPI(InterestPacket packet, Router pendingSrc)
	{
		String uri = packet.contentName;
		if(! pendingInterest.containsKey(uri))
		{
			ArrayList<Router> interfaces = new ArrayList<Router>();
			interfaces.add(pendingSrc);
			pendingInterest.put(uri, interfaces);
		}
		else
		{
			ArrayList<Router> interfaces = pendingInterest.get(uri);
			if(!interfaces.contains(pendingSrc))
			{
				interfaces.add(pendingSrc);
			}
		}
	}
}
