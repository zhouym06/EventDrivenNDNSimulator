package event;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import statistic.Statistic;
import event.Request;
import topology.Server;
import topology.Sink;
import util.MyRandom;

import logger.Logger;

public class Requests {
	public LinkedList<Request> reqs = null;
	
	public Requests()
	{
		reqs = new LinkedList<Request>();
		
	}
	public void add(Request newRequest)
	{
		if(reqs == null)
		{
			reqs = new LinkedList<Request>();
			reqs.add(newRequest);
			Logger.log("TimeLine:" + newRequest.toString() + " added()", Logger.DEBUG - 1);
			return;
		}
		if(reqs.size() == 0)
		{
			reqs.add(newRequest);
			Logger.log("TimeLine:" + newRequest.toString() + " added()", Logger.DEBUG - 1);
			return;
		}
		reqs.addLast(newRequest);
		Logger.log("TimeLine:" + newRequest.toString() + " added()", Logger.DEBUG - 1);
		
	}
	public void sort()
	{
		Collections.sort(reqs);
	}
	
	
}
