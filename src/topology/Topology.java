package topology;

import java.util.ArrayList;

import event.TimeLine;

public class Topology {
	int routerNum;
	int edgeNum;
	int serverNum;
	int sinkNum;
	Server[] servers;
	ArrayList<Edge> edges;	//edge.size() may be larger than edgeNum after server and sink is added
	Router[] routers;
	
	public Topology ()
	{
		
	}
	public Topology (String path)
	{
		
	}
	void loadTopology(String path, int type)
	{
		
	}
	public TimeLine genRequests()
	{
		
		return null;
	}
//	server-0-|-1-
//           |
//           |-2-
//           |
//           |-3-
	public static Topology getDefaultTopology1()
	{
		Topology topo = new Topology();
		topo.routerNum = 4;
		topo.routers = new Router[4];
		topo.serverNum = 1;
		topo.servers = new Server[1];
		topo.edgeNum = 3;
		
		for(int i = 0; i <topo.routerNum; i++)
		{
			topo.routers[i].routerID = i;
			topo.routers[i].interfaceNum = 4;
			topo.routers[i].interfaces = new ArrayList<Edge>();
		}
		Edge e1 = new Edge(topo.routers[0], topo.routers[1]);
		Edge e2 = new Edge(topo.routers[0], topo.routers[2]);
		Edge e3 = new Edge(topo.routers[0], topo.routers[3]);
		topo.routers[0].interfaces.add(e1);
		topo.routers[0].interfaces.add(e2);
		topo.routers[0].interfaces.add(e3);
		topo.routers[1].interfaces.add(e1);
		topo.routers[2].interfaces.add(e2);
		topo.routers[3].interfaces.add(e3);

		//place servers
		//place sinks
		//set contents
		
		
		return topo;
	}
	public TimeLine genDefaultRequests1()
	{
		
		return null;
	}
	
	public void calculateFIB()
	{
		//announce();
	}
	public void execute(TimeLine tl)
	{
		
	}
	

}
