package simulator;

import logger.Logger;
import event.TimeLine;
import topology.Topology;

public class Simulator {
	public static void main(String[] args) {
		Logger.clear();
		//Topology topo = new Topology("path");
		Topology topo = Topology.getDefaultTopology1();
		//TimeLine timeline = topo.genRequests(100000);
		TimeLine timeline = topo.genDefaultRequests1();
		timeline.execute();
	}

}
