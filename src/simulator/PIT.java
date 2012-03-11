package simulator;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import logger.Logger;

import simulator.packet.ContentPacket;
import simulator.packet.InterestPacket;
import topology.Router;

public class PIT {
	HashMap<String, ArrayList<Router>> pendingInterest = null;
	public PIT()
	{
		pendingInterest = new HashMap<String, ArrayList<Router>>();
	}
	public boolean addPI(InterestPacket packet, Router pendingSrc)
	{
		Logger.log("PIT:addPI " + packet.contentName, Logger.DETAIL);
		String uri = packet.contentName;
		if(! pendingInterest.containsKey(uri))
		{
			ArrayList<Router> interfaces = new ArrayList<Router>();
			interfaces.add(pendingSrc);
			pendingInterest.put(uri, interfaces);
			Logger.log("PIT:addPI "  + uri + " fin." + pendingSrc.routerID, Logger.ROUTER - 1);
			return true;
		}
		else
		{
			ArrayList<Router> interfaces = pendingInterest.get(uri);
			if(!interfaces.contains(pendingSrc))
			{
				interfaces.add(pendingSrc);
			}
			pendingInterest.put(uri, interfaces);
			Logger.log("PIT:addPI fin. Interest already sent", Logger.ROUTER - 1);
			return false;
		}
		
	}
	public ArrayList<Router> handle(ContentPacket cPacket) 
	{
		Logger.log("PIT:handleContent " + cPacket.contentName, Logger.DETAIL);
		/*
		 * for(Entry<String, ArrayList<Router>> e:pendingInterest.entrySet())
		{
			Logger.log("\t hasContent " + e.getKey(), Logger.DETAIL);
		}
		 * */
		
		String uri = cPacket.contentName;
		String[] strs = uri.split("-");
		ArrayList<Router> pendingRouters = null;
		if(strs.length == 3)
		{
			pendingRouters = pendingInterest.get(strs[0] + "-" + strs[1]);
		}
		else if(strs.length == 2)
		{
			pendingRouters = pendingInterest.remove(uri);
		}
		else
		{
			Logger.log("!!!PIT:handleContent(): contentName fomat error", Logger.ERROR);
			return null;
		}
		if(pendingRouters == null)
		{
			Logger.log("!!!PIT:handleContent(): we don't have " + uri + " in PIT", Logger.ERROR);
			Logger.log(display(), Logger.ERROR);
			return null;
		}
		return pendingRouters;

		
	}
	public double getFowardTime()
	{  				
		return pendingInterest.size() * 0.01;			//* MyRandom.nextPoisson(10) / 10;?
	}
	public String display()
	{
		String str = "This PIT have:";
		for(Entry<String, ArrayList<Router>> e:pendingInterest.entrySet())
		{
			str += e.getKey();
			str += " of Router:";
			for(Router r:e.getValue())
				str += r.routerID + " ";
		}
		return str;
	}
	
}
