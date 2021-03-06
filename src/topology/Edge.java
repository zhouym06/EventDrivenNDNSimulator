package topology;

import logger.Logger;

public class Edge {
	Router r1;
	Router r2;
	double delay;
	int edgeID;
	public Edge(Router r1, Router r2, int edgeID)
	{
		this(r1, r2, edgeID, 0);
	}
	public Edge(Router r1, Router r2, int edgeID, double delay)
	{
		this.r1 = r1;
		this.r2 = r2;
		this.edgeID = edgeID;
		this.delay = delay;
	}
	public Router theOther(Router r)
	{
		//Logger.log("Edge:theOtherof(router" + r.routerID + ")" + " in edge" + edgeID + " is router" + ( r1.equals(r) == true ? r2.routerID : r1.routerID), Logger.DETAIL);
		if (r1.equals(r))
		{
			return r2;
		}
		else
		{
			return r1;
		}
	}
	public boolean equals(Object o1)
	{
		return ((Edge) o1).edgeID == this.edgeID;
	}
	public void display() {
		Logger.log("Edge" + edgeID + ":" + " between Router" + r1.routerID + " and Router" + r2.routerID + " with delay " + delay, Logger.INFO);
		
	}
}
