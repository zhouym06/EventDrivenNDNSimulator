package simulator;

import java.util.ArrayList;

import logger.Logger;

import simulator.packet.AnnoucePacket;
import simulator.packet.InterestPacket;

public class FIB {								//Forward Information Base
	ArrayList<FIBEntry> entries = null;			//TO DO: use trie to improve performance 
	public FIB()
	{
		entries = new ArrayList<FIBEntry>();
	}
	
	public int getNextInterface(String uri)
	{
		int longestFit = 0;
		int nextInterface = -1;
		for(FIBEntry e:entries)
		{
			if(e.fitLength(uri) > longestFit)
				nextInterface = e.nextInterface;
		}
		return nextInterface;
	}
	public int getSize()
	{
		return entries.size();
	}
	/*
	 * @return if the fib have been changed
	*/
	public boolean announce(AnnoucePacket aPacket, int fromInterface, double arriveTime)
	{
		String uri = aPacket.contentName;
		Logger.log("FIB:announce(" + uri + " from interface" + fromInterface + ")\t" + "arriveTime" + arriveTime, Logger.DEBUG);
		FIBEntry entry = null;
		for(FIBEntry e:entries)
		{
			Logger.log("fitLength" + e.fitLength(uri) + "uri" + uri.length(), Logger.DETAIL);
			if(e.fitLength(uri) == uri.length())
			{
				entry = e;
				break;
			}
		}
		if(entry == null)
		{
			entries.add(new FIBEntry(aPacket.contentName, fromInterface, aPacket.timeLived, arriveTime));
			Logger.log("\tFIB:announce new Entry" , Logger.DEBUG);
			return true;
		}
		else
		{
			if(entry.getNextInterface() == fromInterface)
			{
				Logger.log("\tFIB:announce same Entry" , Logger.DEBUG);
				return false;
			}
			//sorted by ttl first, time second now or arrive time rules?
			if(entry.distance > aPacket.timeLived)
			{
				entry.setNextInterface(fromInterface);
				entry.setDistance(aPacket.timeLived);
				entry.setDelay(arriveTime);
				Logger.log("\tFIB:announce better ttl" , Logger.DEBUG);
				return true;
			}else if(entry.distance == aPacket.timeLived && entry.delay > arriveTime)
			{
				entry.setNextInterface(fromInterface);
				entry.setDistance(aPacket.timeLived);
				entry.setDelay(arriveTime);
				Logger.log("\tFIB:announce better delay" , Logger.DEBUG);
				return true;
			}else
			{
				Logger.log("\tFIB:announce worse perfomance" , Logger.DEBUG);
				return false;
			}
		}
	}

	public void display() {
		for(FIBEntry e:entries)
		{
			Logger.log("\tFIBEntry(" + e.prefix + ") is " + e.nextInterface + " with dis " + e.distance + " and delay " + e.delay, Logger.INFO);
		}
		
	}
}
