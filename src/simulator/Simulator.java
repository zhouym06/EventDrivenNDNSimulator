package simulator;

import logger.Logger;
import event.TimeLine;
import topology.Topology;

public class Simulator {
	public static void main(String[] args) {
		Logger.clear();
		//Topology topo = new Topology("path");
		Topology topo = Topology.getDefaultTopology1();
		int requestNum = 100000;
		double totalRequestTime = 1000; 
		//TimeLine timeline = topo.genOnOffRequests(requestNum, totalRequestTime);
		//TimeLine timeline = topo.genPoissonRequests(requestNum);
		TimeLine timeline = topo.genDefaultRequests1();
		timeline.execute();
	}

}
