package topology;

import java.util.Random;

import event.Task;
import logger.Logger;
import simulator.packet.AnnoucePacket;
import simulator.packet.InterestPacket;


public class Server extends Router{
	String prefix;
	//ContentName as prefix-contentNo-segmentNum
	int contentNum;
	int maxSize;
	Router linkedTo;
	double[] cdf;						//Cumulative distribution function by power law
	int[] contentSize;					//sorted by popularity
							
	public Server(String prefix, int contentNum, int maxSize, Router linkedTo) {
		super(0, 0);
		
		this.prefix = prefix;
		this.contentNum = contentNum;
		this.maxSize = maxSize;
		this.linkedTo = linkedTo;
		genPossibility();
		genContentSize();
	}
	
	private void genPossibility() {
		Logger.log("Server:" + "genPossibility()", Logger.DEBUG);
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
		Logger.log("\tgenPossibility(): sum is" + sum, Logger.DEBUG);
	}
	private void genContentSize() {
		//in a decreasing reciprocal manner(1/x)
		//http://www.php-oa.com/2010/05/27/squid-cache-object-size.html
		//http://www.cs.yale.edu/homes/jqhan/paper/ftp.pdf
		Logger.log("Server:" + "genContentSize()", Logger.DEBUG);
		contentSize = new int[contentNum];
		Random random = new Random(System.currentTimeMillis());
		double[] size = new double[contentNum];
		for(int i = 0; i < contentNum; i++)
		{
			size[i] = ( random.nextDouble() * (maxSize-1) ) + ( 1 / (double)maxSize );
			contentSize[i] = (int)(Math.ceil(1 / size[i]));
			//Logger.log("\tgenContentSize(" + i + "):" + "size" + size[i], Logger.VERY_DETAIL);
			//Logger.log("\tgenContentSize(" + i + "):" + "contentSize" + contentSize[i], Logger.VERY_DETAIL);
		}
	}
	public void handle(InterestPacket iPacket, Router from, double time)	
	{
		if(!iPacket.contentName.contains(prefix))
		{
			Logger.log(iPacket.contentName + " Not in Server " + prefix, Logger.ERROR);
			return;
		}
		//add(Task newTask)
		//linkedTo.handle(new ContentPacket());
	}
	
	public void announce()	
	{
		
	}
	
	public int getContentNum()
	{
		return contentNum;
	}
	
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

}
