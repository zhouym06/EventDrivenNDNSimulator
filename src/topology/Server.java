package topology;

import logger.Logger;
import simulator.packet.AnnoucePacket;
import simulator.packet.InterestPacket;


public class Server extends Router{
	String prefix;
	int contentNum;
	int maxSize;
	Router linkedTo;
	int[] contentSize;					//sorted by popularity
							
	public Server(String prefix, int contentNum, int maxSize, Router linkedTo) {
		this.prefix = prefix;
		this.contentNum = contentNum;
		this.maxSize = maxSize;
		this.linkedTo = linkedTo;
	}
	public void handle(InterestPacket iPacket, Router from)	
	{
		if(!iPacket.contentName.contains(prefix))
		{
			Logger.log(iPacket.contentName + " Not in Server " + prefix, Logger.ERROR);
			return;
		}
		
	}
	public void handle(AnnoucePacket aPacket, Router from)	
	{
		aPacket.timeLived++;
		
		
	}

	private void genData()					//生成幂律分布的内容数据
	{
		//ContentName as prefix-contentNo-segmentNum
		int[] cSize = new int[contentNum];	//顺序排列的
		
		
	}

}
