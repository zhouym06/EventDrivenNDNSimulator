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
			int contentNum = 1000;
			int[] cacheSizes = new int[10];
			for(int j = 0; j < 10; j++)
			{
				cacheSizes[j] = 0;
			}
			cacheSizes[i] = 10;
			topo[i] = Topology.getLineTopology(routerNum, serverNum, contentNum, cacheSizes);
			//topo[i].displayTopology();
		}
		
		Logger.log("generate Requests", Logger.INFO);
		Requests r[] = new Requests[10];
		int requestNum = 10000;
		int totalRequestTime = 10000;
		int maxContentNum = 1000;
		for(int i = 0; i < 10; i++)
		{
			r[i] = RequestGenerator.getPoissonRequests(requestNum, maxContentNum, totalRequestTime);
		}
		//init TimeLine
		int[][] totalReq = new int [10][10];
		int[][] totalHit = new int [10][10];
		int[][] totalMiss = new int [10][10];
		long[][] totalTTL = new long [10][10];
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				TimeLine.clear();
				Logger.setFile("topo" + String.valueOf(i) + "-req" + String.valueOf(j) + ".txt");
				topo[i].setTimeLine(r[j]);
				Statistic.init(topo[i].contentNum);
				TimeLine.execute();
				Statistic.display();
				
				totalReq[i][j] = Statistic.totalRequest;
				totalHit[i][j] = Statistic.totalCacheHit;
				totalMiss[i][j] = Statistic.totalCacheMiss;
				totalTTL[i][j] = Statistic.totalTTL;
			}
			
		}
		System.out.println("totalRequest");
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(totalReq[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("totalCacheHit");
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(totalHit[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("totalMiss");
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(totalMiss[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("totalTTL");
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(totalTTL[i][j]);
				System.out.print("\t");
			}
			System.out.println();
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
