package simulator;

import logger.Logger;
import event.Requests;
import event.TimeLine;
import statistic.Statistic;
import topology.RequestGenerator;
import topology.Topology;

public class Simulator {
	public static void main(String[] args) {
		/**/
		//Logger.setFile("log.txt");
		Logger.clear();
		
		//Logger.log("generate Topology", Logger.INFO);
		//Topology topo = new Topology("path");
		Topology topo[] = new Topology[10];
		//topo[0] = Topology.getDefaultTopology1();
		for(int i = 0; i < 10; i++)
		{
			Logger.setFile(String.valueOf(i) + ".txt");
			int routerNum = 10;
			int serverNum = 1;
			int contentNum = 100;
			int[] cacheSizes = new int[10];
			cacheSizes[i] = 10;
			topo[i] = Topology.getLineTopology(routerNum, serverNum, contentNum, cacheSizes);
			//topo[i].displayTopology();
		}
		
		Logger.log("generate Requests", Logger.INFO);
		Requests r[] = new Requests[10];
		int requestNum = 10000;
		int totalRequestTime = 10000;
		int maxContentNum = 100;
		for(int i = 0; i < 10; i++)
		{
			r[i] = RequestGenerator.getPoissonRequests(requestNum, maxContentNum, totalRequestTime);
		}
		//init TimeLine
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				TimeLine.clear();
				Logger.setFile("topo" + String.valueOf(i) + "-req" + String.valueOf(j) + ".txt");
				topo[i].setTimeLine(r[j]);
				Statistic.init(topo[0].contentNum);
				TimeLine.execute();
				Statistic.display();				
			}
			
		}
		
		
		
		/*
		Logger.clear();
		Logger.log("generate Topology", Logger.INFO);
		//Topology topo = new Topology("path");
		Topology topo = Topology.getDefaultTopology1();
		Statistic.init(topo.contentNum);
		Logger.log("generate Requests", Logger.INFO);
		//int requestNum = 100000;
		//double totalRequestTime = 1000; 
		//topo.genOnOffRequests(requestNum, totalRequestTime);
		//topo.genPoissonRequests(requestNum);
		topo.genDefaultRequests1();
		//RequestGenerator.genDefaultRequests1(topo);		//init TimeLine
		//topo.displayTopology();
		//topo.displayFIB();
		TimeLine.execute();
		Logger.log("TimeLine executed", Logger.INFO);
		Statistic.display();
		*/
	}

}
