package szTopoOpt;

import topology.Topology;

public class Main {
	public static void main(String[] args) {
		System.out.println("szTopoOpt");
		int cacheSizes[] = new int[34];
		for(int i = 0; i < 33; i ++)
			cacheSizes[i] = 1000 / 34;
		Topology topo = Topology.getSzCampusTopology1(1000, cacheSizes);
		//topo.displayTopology();
		//topo.displayFIB();

	}

}
