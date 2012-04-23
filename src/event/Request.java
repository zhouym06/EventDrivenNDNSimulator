package event;

import simulator.packet.InterestPacket;
import topology.Router;
import logger.Logger;

public class Request implements Comparable{
	double time;
	int ContentNo;
	public Request(int ContentNo, double time)
	{
		this.time = time;
		this.ContentNo = ContentNo;
	}
	public String toString()
	{
		return time + "\tRequest(" + ContentNo + ")";
	}
	
	
	public void display() {
		Logger.log(System.out, String.valueOf(time), 3);
	}
	public double getTime()
	{
		return time;
	} 
	public int getContentNo()
	{
		return ContentNo;
	}
	public int compareTo(Object arg0) {
		if(time < ((Request)arg0).time)
			return -1;
		else if(time > ((Request)arg0).time)
			return 1;
		return 0;
	}

}
