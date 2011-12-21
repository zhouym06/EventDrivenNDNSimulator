package topology;

import java.util.ArrayList;
import java.util.Random;

import logger.Logger;

import event.InterestTask;
import event.TimeLine;

public class Topology {
	int routerNum;
	int edgeNum;
	int serverNum;
	Server[] servers;
	ArrayList<Edge> edges; // edge.size() may be larger than edgeNum after
							// server and sink is added
	Router[] routers;
	Sink[] sinks;

	public Topology() {

	}

	public Topology(String path) {

	}

	void loadTopology(String path, int type) {

	}

	public TimeLine genOnOffRequests(int requestNum, double totalRequestTime) {
		TimeLine tl = new TimeLine();
		int sNo, rNo, cNo;
		Random random = new Random(System.currentTimeMillis());

		int requestCount = 0;
		while (requestCount < requestNum) {
			// the moment that "on" started
			double onTime = totalRequestTime * random.nextDouble();
			// routerNo that is on
			// uniform distributed
			rNo = (int) Math.floor(random.nextDouble() * routerNum);
			// server that is requested when it's on
			// uniform distributed
			sNo = (int) Math.floor(random.nextDouble() * serverNum);
			// the time "on" lasts is Exponential distributed
			double lamda = 1;
			double lastTime = nextExp(lamda);
			double time = 0;
			while (time < lastTime && requestCount < requestNum) {
				// Hit of contentNo is distributed by power law in each server
				cNo = (int) Math.floor(servers[sNo].getContentNo(random
						.nextDouble()));
				// ContentName as prefix-contentNo
				String uri = servers[sNo].prefix + "-" + String.valueOf(cNo);
				// 10 request is generated each second when on?
				time += nextPoisson(1) / 10;
				tl.add(new InterestTask(uri, sinks[rNo].linkedTo, sinks[rNo],
						onTime + time, tl));
				requestCount++;
				Logger.log("addTask(" + time + ")" + "to\tRouter" + rNo + "\trequest:"
						+ uri + "\tat " + (onTime + time) + "\tbefore:" + lastTime, Logger.DEBUG);
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
			Logger.log("addTask(" + i + ")" + "to\tRouter" + rNo + "\trequest:"
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

	// server-0-|-1-sink1
	// |
	// |-2-sink2
	// |
	// |-3-sink3
	public static Topology getDefaultTopology1() {
		Logger.log("Topology:" + "getDefaultTopology1()", Logger.DEBUG);
		Topology topo = new Topology();
		// routers servers and sinks
		topo.routerNum = 4;
		topo.routers = new Router[topo.routerNum];
		topo.serverNum = 1;
		topo.servers = new Server[topo.serverNum];
		topo.edgeNum = 3;
		topo.sinks = new Sink[topo.routerNum];

		for (int i = 0; i < topo.routerNum; i++) {
			topo.routers[i] = new Router(i, 10);
			topo.routers[i].interfaceNum = 4;
			topo.routers[i].interfaces = new ArrayList<Edge>();
			topo.sinks[i] = new Sink();
		}
		for (int i = 0; i < topo.serverNum; i++) {
			topo.servers[i] = new Server("Server" + String.valueOf(i), 100, 10,
					topo.routers[0]);
		}
		// interfaces
		Edge e1 = new Edge(topo.routers[0], topo.routers[1]);
		Edge e2 = new Edge(topo.routers[0], topo.routers[2]);
		Edge e3 = new Edge(topo.routers[0], topo.routers[3]);
		topo.routers[0].interfaces.add(e1);
		topo.routers[0].interfaces.add(e2);
		topo.routers[0].interfaces.add(e3);
		topo.routers[1].interfaces.add(e1);
		topo.routers[2].interfaces.add(e2);
		topo.routers[3].interfaces.add(e3);
		return topo;
	}

	public TimeLine genDefaultRequests1() {
		Logger.log("Topology:" + "genDefaultRequests1()", Logger.DEBUG);
		int requestNum = 1000;
		// TimeLine tl = genPoissonRequests(requestNum);
		TimeLine tl = genOnOffRequests(requestNum, 10);
		return tl;
	}

	public void calculateFIB() {
		// announce();
	}

}
