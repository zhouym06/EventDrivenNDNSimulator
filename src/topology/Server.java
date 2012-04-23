package topology;


import event.ContentTask;
import event.InterestTask;
import event.TimeLine;

import logger.Logger;
import simulator.packet.AnnoucePacket;
import simulator.packet.ContentPacket;
import simulator.packet.InterestPacket;
import util.MyRandom;


public class Server extends Router{
	String prefix;
	//ContentName as prefix-contentNo-segmentNum
	//int contentNum;
	//int maxSize;
	//double[] cdf;						//Cumulative distribution function by power law//sorted by popularity
	//int[] contentSize;					
							
	public Server(String prefix, int ServerNo) {
		super(ServerNo, 0);
		
		this.prefix = prefix;
		//this.contentNum = contentNum;
		//this.maxSize = maxSize;
		//initPossibility();
		//initContentSize();
	}
	//不分segment了
	public void handle(InterestTask iTask, Router from)	
	{
		Logger.log("Server:handleInterest(" + iTask.iPacket.contentName+ ") from router" + from.routerID + " at router" + this.routerID, Logger.ROUTER);
		String uri = iTask.iPacket.contentName;
		String[] a = uri.split("-");
		if(!a[0].equalsIgnoreCase(prefix))
		{
			Logger.log("!!!Server:handleInterest(): Unknown Content:" + uri +" in Server" + routerID + "(" + prefix + ")", Logger.ERROR);
			return;
		}
		int contentNo = Integer.valueOf(a[1]);
		double time = iTask.getTime();
		/*//不分segment了
		for(int segNo = 0; segNo < contentSize[contentNo]; segNo++)
		{
			ContentPacket cPacket = new ContentPacket(prefix + "-" + contentNo + "-" + segNo);
			time += getServeTime();	 	 				//* MyRandom.nextPoisson(10) / 10;
			TimeLine.add( new ContentTask(cPacket, from, time));
		}
		*/
		time += getServeTime();
		ContentTask t = new ContentTask(new ContentPacket(prefix + "-" + contentNo, 1), from, time);
		//ContentTask t = new ContentTask(new ContentPacket(prefix + "-" + contentNo, contentSize[contentNo]), from, time);
		TimeLine.add(t);
	}
	public double getServeTime()
	{
		return 0.1;
		//MyRandom.nextPoisson(10) / 100;
	}
	/*
	private void initPossibility() {		//Cumulative distribution function by power law
		Logger.log("Server:" + "initPossibility()", Logger.ROUTER);
		cdf = new double[contentNum];
		double sum = 0;
		for(int i = 0; i < contentNum; i++)	//a Harmonic series 
		{
			sum += 1/ ((double)i + 1);
		}
		cdf[0] = 1/ sum;
		sum = cdf[0];
		for(int i = 1; i < contentNum; i++)
		{
			cdf[i] =  cdf[i - 1] + cdf[0] / (i + 1);
			sum += cdf[0] / (i + 1);
		}
		Logger.log("\tgenPossibility(): sum is" + sum, Logger.ROUTER);
	}
	
	private void initContentSize() {
		Logger.log("Server:" + "initContentSize()", Logger.ROUTER);
		contentSize = new int[contentNum];
		for(int i = 0; i < contentNum; i++)
		{
			//even distributed
			contentSize[i] = (int) Math.floor((MyRandom.nextDouble() * maxSize));
			
			// to do or not to do
			//in a decreasing reciprocal manner(1/x)
			//http://www.php-oa.com/2010/05/27/squid-cache-object-size.html
			//http://www.cs.yale.edu/homes/jqhan/paper/ftp.pdf
			//double tmp = ( MyRandom.nextDouble() * (maxSize-1) ) + ( 1 / (double)maxSize );
			//contentSize[i] = (int)(Math.ceil(1 / tmp));
			
			//Logger.log("\tinitContentSize(" + i + "):" + "size" + size[i], Logger.VERY_DETAIL);
			Logger.log("\tinitContentSize(" + i + "):" + "contentSize" + contentSize[i], Logger.VERY_DETAIL);
		}
	}
	*/
	public void announce(double time) {
		AnnoucePacket ap = new AnnoucePacket(prefix);
		for(Edge e:interfaces)
		{
			e.theOther(this).handle(ap, e, e.delay);
		}
	}
	/*
	public int getContentNo(double p)//by binary search
	{
		int result = getContentNo(p, 0, contentNum);
		//Logger.log("Server:" + "getContentNo()" + p + ":" + result, Logger.DETAIL);
		return result;
	}
	
	private int getContentNo(double p, int begin, int end)
	{
		if(begin == end)
			return begin;
		if(begin == end - 1)
		{
			return p < cdf[begin] ? begin : end;
		}
		int mid = (int) Math.round(begin + ((double) (end - begin)) * 0.3);
		return p < cdf[mid] ? getContentNo(p, begin, mid) : getContentNo(p, mid, end);
	}
*/
	public void  display()
	{
		Logger.log("Server" + routerID + ": as " + prefix + " has " + interfaces.size() + " edges", Logger.INFO);
		for(Edge e: interfaces)
		{
			e.display();
		}
	}

}
