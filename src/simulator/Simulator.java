package simulator;

import event.TimeLine;
import topology.Topology;

public class Simulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Topology topo = new Topology("path");
		Topology topo = Topology.getDefaultTopology1();
		TimeLine timeline = new TimeLine();
		topo.execute(timeline);
		// TODO �Զ����ɷ������

	}

}
