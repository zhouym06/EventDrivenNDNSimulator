package topology;

import java.util.Random;

import logger.Logger;
import statistic.Statistic;
import util.MyRandom;
import event.InterestTask;
import event.Requests;
import event.TimeLine;

public class RequestGenerator {
	double[] cdf;						//Cumulative distribution function by power law//sorted by popularity
	//TODO: rewrite for server distribution
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**/
	public static void genDefaultRequests1(Topology topo) {
		Logger.log("Topology:" + "genDefaultRequests1()", Logger.INFO);
		int requestNum = 1000;
		//RequestGenerator.genPoissonRequests(requestNum, serverNum, sinkNum, servers, sinks);
		genOnOffRequests(requestNum, 1000, topo.serverNum, topo.routerNum, topo.sinkNum, topo.servers, topo.sinks);
	}
	
	public static Requests genOnOffRequests(int requestNum, double totalRequestTime, int serverNum, int routerNum, int sinkNum, Server[] servers, Sink[] sinks) {
		Requests r = new Requests();
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
			// TODO: Cumulative distribution function by power law
			serverNo = (int) Math.floor(random.nextDouble() * serverNum);
			// the time "on" lasts is Exponential distributed
			double lamda = 1;
			double lastTime = MyRandom.nextExp(lamda);
			double time = 0;
			while (time < lastTime && requestCount < requestNum) {
				// Hit of contentNo is distributed by power law in each server
				contentNo = (int) Math.floor(servers[serverNo].getContentNo(random
						.nextDouble()));
				Statistic.countRequest(contentNo);
				// ContentName as prefix-contentNo
				String uri = servers[serverNo].prefix + "-" + String.valueOf(contentNo);
				// 10 request is generated each second when on?
				time += MyRandom.nextPoisson(1) / 10;
				r.add(new InterestTask(uri, sinks[sinkNo].linkedTo, sinks[sinkNo],
						onTime + time));
				//TimeLine.add(new InterestTask(uri, sinks[sinkNo].linkedTo, sinks[sinkNo],
				//		onTime + time));
				requestCount++;
				//Logger.log("Topology:genOnOffRequests(" + "" + ")" + "to\tSink" + sinkNo + "\treq Content:"
				//r		+ uri + "\tat " + (onTime + time), Logger.DETAIL);
			}
		}
		return r;
	}

	

	public static void genPoissonRequests(int requestNum, int serverNum, int sinkNum, Server[] servers, Sink[] sinks) {
		TimeLine.clear();
		
		int sNo, rNo, cNo;
		
		double time = 0;
		// Logger.log("c:" + Math.exp(-1), Logger.DETAIL);
		// ContentName as prefix-contentNo
		// Hit of contentNo is distributed by power law in each server
		// TODO: also for servers
		for (int i = 0; i < requestNum; i++) {
			sNo = (int) Math.floor(MyRandom.nextDouble() * serverNum);
			//rNo = (int) Math.floor(MyRandom.nextDouble() * routerNum);
			int sinkNo = (int) Math.floor(MyRandom.nextDouble() * sinkNum);
			cNo = (int) Math.floor(servers[sNo].getContentNo(MyRandom
					.nextDouble()));
			String uri = servers[sNo].prefix + "-" + String.valueOf(cNo);
			// 10 request is generated each second?
			time += MyRandom.nextPoisson(1) / 10; 
			TimeLine.add(new InterestTask(uri, sinks[sinkNo].linkedTo, sinks[sinkNo], time));
			Logger.log("Topology:genPoissonRequests(" + i + ")" + "to\tSink" + sinkNo + "\trequest:"
					+ uri + "\tat " + time, Logger.DEBUG);
		}
	}
	
}
