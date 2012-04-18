package topology;

import java.util.ArrayList;
import java.util.Random;

import util.MyRandom;

import logger.Logger;

import event.InterestTask;
import event.Requests;
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
	
	public int contentNum;
	int[] serverOfContent;

	public Topology() {
		
	}

	void loadTopology(String path, int type) {
		//TODO:

	}

	// server0-0-|-1-sink0
	// 		     |
	// 			 |-2-sink1
	// 			 |
	// 			 |-3-sink2
	public static Topology getDefaultTopology1() {
		Logger.log("Topology:" + "getDefaultTopology1()", Logger.INFO);
		Topology topo = new Topology();
		topo.routerNum = 4;
		topo.routers = new Router[topo.routerNum];
		
		topo.serverNum = 1;
		topo.servers = new Server[topo.serverNum];
		
		topo.contentNum = 100;
		topo.serverOfContent = new int[topo.contentNum];
		for(int i = 0; i < 100; i++)
			topo.serverOfContent[i] = 0;
		topo.issueContentOnServer();
		
		topo.sinkNum = 3;
		topo.sinks = new Sink[topo.sinkNum];
		// routers
		for (int routerID = 0; routerID < topo.routerNum; routerID++) {
			int cacheSize = 190;
			topo.routers[routerID] = new Router(routerID, cacheSize);
		}
		//sinks
		for (int i = 0; i < topo.sinkNum; i++) {
			topo.sinks[i] = new Sink(topo.routers[i+1], -(i+1));
		}
		//servers
		topo.servers[0] = new Server("Server" + String.valueOf(0), -100, topo.contentNum, 10);
		// interfaces
		topo.edgeNum = 6;
		Random random = new Random(System.currentTimeMillis());
			//between routers
		for (int i = 1; i < topo.routerNum; i++)
		{
			Edge e = new Edge(topo.routers[0], topo.routers[i], i ,random.nextDouble());
			topo.routers[0].interfaces.add(e);
			topo.routers[i].interfaces.add(e);
			//topo.routers[i].interfaceNum = topo.routers[i].interfaces.size();
		}
			//edges
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
			topo.sinks[i].interfaces.add(e);
		}
		
		topo.announce();
		return topo;
	}

	public static Topology getLineTopology(int routerNum, int serverNum, int contentNum, int[] cacheSizes) {
		Logger.log("Topology:" + "getLineTopology()", Logger.INFO);
		Topology topo = new Topology();
		topo.routerNum = routerNum;
		topo.routers = new Router[topo.routerNum];
		
		topo.serverNum = serverNum;
		topo.servers = new Server[topo.serverNum];
		
		topo.contentNum = contentNum;
		topo.serverOfContent = new int[topo.contentNum];
		for(int i = 0; i < contentNum; i++)
			topo.serverOfContent[i] = 0;
		topo.issueContentOnServer();
		
		topo.sinkNum = routerNum;
		topo.sinks = new Sink[topo.sinkNum];
		// routers
		for (int routerID = 0; routerID < topo.routerNum; routerID++) {
			int cacheSize = 190;
			topo.routers[routerID] = new Router(routerID, cacheSize);
		}
		//sinks
		for (int i = 0; i < topo.sinkNum; i++) {
			topo.sinks[i] = new Sink(topo.routers[i+1], -(i+1));
		}
		//servers
		topo.servers[0] = new Server("Server" + String.valueOf(0), -100, topo.contentNum, 10);
		// interfaces
		topo.edgeNum = 6;
		Random random = new Random(System.currentTimeMillis());
			//between routers
		for (int i = 1; i < topo.routerNum; i++)
		{
			Edge e = new Edge(topo.routers[0], topo.routers[i], i ,random.nextDouble());
			topo.routers[0].interfaces.add(e);
			topo.routers[i].interfaces.add(e);
			//topo.routers[i].interfaceNum = topo.routers[i].interfaces.size();
		}
			//edges
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
			topo.sinks[i].interfaces.add(e);
		}
		
		topo.announce();
		return topo;
	}


	private void issueContentOnServer() {
		// TODO Auto-generated method stub
		
	}

	public void announce() {
		for(int i = 0; i < serverNum; i++)
		{
			Logger.log("Topology:" + "announce(" + servers[i].prefix + ") at Server" + i, Logger.DEBUG);
			servers[i].announce(0);
		}
	}
	public void displayTopology()
	{
		Logger.log("Topology:displayTopology()", Logger.INFO);
		for(int i = 0; i < routerNum; i++)
		{
			routers[i].display();
		}
		Logger.log("", Logger.INFO);
		for(int i = 0; i < serverNum; i++)
		{
			servers[i].display();
		}
		Logger.log("", Logger.INFO);
		for(int i = 0; i < sinkNum; i++)
		{
			sinks[i].display();
		}
		Logger.log("", Logger.INFO);
	}

	public void displayFIB() {
		for(int i = 0; i < routerNum; i++)
		{
			routers[i].displayFIB();
		}
	}
	
	public Requests getOnOffRequests(int requestNum, int totalRequestTime)
	{
		Logger.log("Topology:" + "getOnOffRequests()", Logger.INFO);
		return RequestGenerator.genOnOffRequests(requestNum, totalRequestTime, serverNum, routerNum, sinkNum, servers, sinks);
	}
	public void genDefaultRequests1() {
		Logger.log("Topology:" + "genDefaultRequests1()", Logger.INFO);
		int requestNum = 10000;
		int requestTime = 10000;
		//RequestGenerator.genPoissonRequests(requestNum, serverNum, sinkNum, servers, sinks);
		Requests r = RequestGenerator.genOnOffRequests(requestNum, requestTime, serverNum, routerNum, sinkNum, servers, sinks);
		
		TimeLine.set(r);
	}

}
