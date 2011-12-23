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
		//topo.genOnOffRequests(requestNum, totalRequestTime);
		//topo.genPoissonRequests(requestNum);
		topo.genDefaultRequests1();		//init TimeLine
		//topo.displayTopology();
		//topo.displayFIB();
		TimeLine.execute();
	}

}
