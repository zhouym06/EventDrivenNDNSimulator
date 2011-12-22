package topology;

import java.util.ArrayList;
import java.util.Random;

import logger.Logger;

import event.InterestTask;
import event.TimeLine;

public class Topology {
	int routerNum;
	Router[] routers;
	//// edge.size() may be larger than edgeNum after server and sink is added? == false now
	int edgeNum;
	ArrayList<Edge> edges;
	int serverNum;
	Server[] servers;
	
	int sinkNum;
	Sink[] sinks;

	public Topology() {

	}

	public Topology(String path) {

	}

	void loadTopology(String path, int type) {

	}

	public TimeLine genOnOffRequests(int requestNum, double totalRequestTime) {
		TimeLine tl = new TimeLine();
		int serverNo, sinkNo, contentNo;
		Random random = new Random(System.currentTimeMillis());

		int requestCount = 0;
		while (requestCount < requestNum) {
			// the moment that "on" started
			double onTime = totalRequestTime * random.nextDouble();
			// sinkNo that is on
			// uniform distributed
			sinkNo = (int) Math.floor(random.nextDouble() * sinkNum);
			// server that is requested when it's on
			// uniform distributed
			serverNo = (int) Math.floor(random.nextDouble() * serverNum);
			// the time "on" lasts is Exponential distributed
			double lamda = 1;
			double lastTime = nextExp(lamda);
			double time = 0;
			while (time < lastTime && requestCount < requestNum) {
				// Hit of contentNo is distributed by power law in each server
				contentNo = (int) Math.floor(servers[serverNo].getContentNo(random
						.nextDouble()));
				// ContentName as prefix-contentNo
				String uri = servers[serverNo].prefix + "-" + String.valueOf(contentNo);
				// 10 request is generated each second when on?
				time += nextPoisson(1) / 10;
				tl.add(new InterestTask(uri, sinks[sinkNo].linkedTo, sinks[sinkNo],
						onTime + time, tl));
				requestCount++;
				Logger.log("Topology:genOnOffRequests(" + "" + ")" + "to\tSink" + sinkNo + "\treq Content:"
						+ uri + "\tat " + (onTime + time), Logger.DETAIL);
			}
		}
		return tl;
	}

	private double nextExp(double lamda) {
		double z = Math.random();
		double x = -(1 / lamda) * Math.log(z);
		Logger.log("nextExp(" + lamda + "):" + x, Logger.VERY_DETAIL);
		return x;
	}

	public TimeLine genPoissonRequests(int requestNum) {
		TimeLine tl = new TimeLine();
		int sNo, rNo, cNo;
		Random random = new Random(System.currentTimeMillis());
		double time = 0;
		// Logger.log("c:" + Math.exp(-1), Logger.DETAIL);

		// ContentName as prefix-contentNo
		// Hit of contentNo is distributed by power law in each server
		for (int i = 0; i < requestNum; i++) {
			sNo = (int) Math.floor(random.nextDouble() * serverNum);
			rNo = (int) Math.floor(random.nextDouble() * routerNum);
			cNo = (int) Math.floor(servers[sNo].getContentNo(random
					.nextDouble()));
			String uri = servers[sNo].prefix + "-" + String.valueOf(cNo);
			// 10 request is generated each second?
			time += nextPoisson(1) / 10; 
			tl.add(new InterestTask(uri, sinks[rNo].linkedTo, sinks[rNo], time,
					tl));
			Logger.log("Topology:genPoissonRequests(" + i + ")" + "to\tRouter" + rNo + "\trequest:"
					+ uri + "\tat " + time, Logger.DEBUG);
		}
		return tl;
	}

	private double nextPoisson(double lamda) {
		double x = 0, b = 1, c = Math.exp(-lamda), u;
		do {
			u = Math.random();
			b *= u;
			if (b >= c)
				x++;
		} while (b >= c);
		// Logger.log("nextPoisson(" + lamda + "):" + x, Logger.VERY_DETAIL);
		if (x == 0)
			return 0.01;
		return x;
	}

	// server0-0-|-1-sink0
	// 		     |
	// 			 |-2-sink1
	// 			 |
	// 			 |-3-sink2
	public static Topology getDefaultTopology1() {
		Logger.log("Topology:" + "getDefaultTopology1()", Logger.DEBUG);
		Topology topo = new Topology();
		topo.routerNum = 4;
		topo.routers = new Router[topo.routerNum];
		topo.serverNum = 1;
		topo.servers = new Server[topo.serverNum];
		topo.sinkNum = 3;
		topo.sinks = new Sink[topo.sinkNum];
		// routers
		for (int routerID = 0; routerID < topo.routerNum; routerID++) {
			int cacheSize = 10;
			topo.routers[routerID] = new Router(routerID, cacheSize);
		}
		//sinks
		for (int i = 0; i < topo.sinkNum; i++) {
			topo.sinks[i] = new Sink(topo.routers[i+1]);
		}
		//servers
		topo.servers[0] = new Server("Server" + String.valueOf(0), 100, 10);
		// interfaces
		topo.edgeNum = 6;
		Random random = new Random(System.currentTimeMillis());
			//between routers
		for (int i = 1; i < topo.routerNum; i++)
		{
			Edge e = new Edge(topo.routers[0], topo.routers[i], i ,random.nextDouble());
			topo.routers[0].interfaces.add(e);
			topo.routers[i].interfaces.add(e);
			topo.routers[i].interfaceNum = topo.routers[i].interfaces.size();
		}
			//servers
		{
			Edge e = new Edge(topo.routers[0], topo.servers[0], -1, random.nextDouble());
			topo.routers[0].interfaces.add(e);
			topo.servers[0].interfaces.add(e);
		}
			//sinks
		for (int i = 0; i < topo.sinkNum; i++)
		{
			Edge e = new Edge(topo.routers[i + 1], topo.sinks[i], -1, random.nextDouble());
			topo.routers[i + 1].interfaces.add(e);
			topo.routers[i].interfaces.add(e);
		}
		
		topo.announce();
		return topo;
	}

	public TimeLine genDefaultRequests1() {
		Logger.log("Topology:" + "genDefaultRequests1()", Logger.DEBUG);
		int requestNum = 1000;
		// TimeLine tl = genPoissonRequests(requestNum);
		TimeLine tl = genOnOffRequests(requestNum, 10);
		return tl;
	}

	public void announce() {
		for(int i = 0; i < serverNum; i++)
		{
			servers[i].announce(0);
		}
		// announce();
	}

}
