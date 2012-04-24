package simulator;

import logger.Logger;
import event.RequestGenerator;
import event.Requests;
import event.TimeLine;
import statistic.Statistic;
import topology.Topology;

public class Simulator {
	public static void main(String[] args) {
		/**/
		//Logger.setFile("log.txt");
		Logger.clear();
		//Logger.log("generate Topology", Logger.INFO);
		//Topology topo = new Topology("path");
		
		//topo[0] = Topology.getDefaultTopology1();
		int contentNum = 1000;
		int requestsNum = 20;
		int topoNum = 99;
		Topology topo[] = new Topology[topoNum];
		Statistic.init(contentNum);//contentNum
		/*
		for(int i = 0; i < 10; i++)
		{
			Logger.setFile(String.valueOf(i) + ".txt");
			int routerNum = 10;
			int serverNum = 1;
			
			int[] cacheSizes = new int[10];
			for(int j = 0; j < 10; j++)
			{
				cacheSizes[j] = 1;
			}
			topo[i] = Topology.getLineTopology(routerNum, serverNum, contentNum, cacheSizes);
			//topo[i].displayTopology();
		}*/
		int[][] cacheSizes = new int[topoNum][3];
		for(int i = 0; i < topoNum / 3; i++)
		{
			cacheSizes[i][0] = 2*i;
			cacheSizes[i][1] = i;
			cacheSizes[i][2] = i;
			
			cacheSizes[i+topoNum / 3][0] = i;
			cacheSizes[i+topoNum / 3][1] = 2*i;
			cacheSizes[i+topoNum / 3][2] = i;
	
			cacheSizes[i+topoNum * 2 / 3][0] = i;
			cacheSizes[i+topoNum * 2 / 3][1] = i;
			cacheSizes[i+topoNum * 2 / 3][2] = 2*i;
		}
		/*
		for(int i = 0; i < topoNum / 3; i++)
		{
			cacheSizes[i*3+0][0] = i;
			cacheSizes[i*3+0][1] = 0;
			cacheSizes[i*3+0][2] = 0;
			
			cacheSizes[i*3+1][0] = 0;
			cacheSizes[i*3+1][1] = i;
			cacheSizes[i*3+1][2] = 0;
	
			cacheSizes[i*3+2][0] = 0;
			cacheSizes[i*3+2][1] = 0;
			cacheSizes[i*3+2][2] = i;
		}
		*/
		/*
		for(int i = 0; i < 3; i++)
		{
			cacheSizes[i*3+0][0] = i*3;
			cacheSizes[i*3+0][1] = i*3;
			cacheSizes[i*3+0][2] = i*3;
			
			cacheSizes[i*3+1][0] = i*3+1;
			cacheSizes[i*3+1][1] = i*3+1;
			cacheSizes[i*3+1][2] = i*3+1;
	
			cacheSizes[i*3+2][0] = i*3+2;
			cacheSizes[i*3+2][1] = i*3+2;
			cacheSizes[i*3+2][2] = i*3+2;
		}
		cacheSizes[9][0] = 50;
		cacheSizes[9][1] = 50;
		cacheSizes[9][2] = 50;
		
		*/
		for(int i = 0; i < topoNum; i++)
		{
			Logger.setFile(String.valueOf(i) + ".txt");
			int serverNum = 1;
			int treeLevel = 3;
			int treeDegree = 3;

			topo[i] = Topology.getTreeTopology(treeLevel, treeDegree, serverNum, contentNum, cacheSizes[i]);
			//topo[i] = Topology.getLineTopology(routerNum, serverNum, contentNum, cacheSizes);
			//topo[i] = Topology.getDefaultTopology1(contentNum);
			//topo[i].displayTopology();
		}
		
		
		Logger.log("generate Requests", Logger.INFO);
		
		Requests r[] = new Requests[requestsNum];
		int requestNum = 2000;
		int totalRequestTime = 2000;
		int maxContentNum = 1000;
		for(int i = 0; i < requestsNum; i++)
		{
			r[i] = RequestGenerator.getPoissonRequests(requestNum, maxContentNum, totalRequestTime);
			//r[i] = RequestGenerator.genOnOffRequests(requestNum, maxContentNum, totalRequestTime);
		}
		/*
		System.out.println("totalRequest");
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(r[j].reqs.size());
				System.out.print("\t");
			}
			System.out.println();
		}
		*/
		//init TimeLine
		int[][] totalReq = new int [topoNum][requestsNum];
		int[][] totalHit = new int [topoNum][requestsNum];
		int[][] totalMiss = new int [topoNum][requestsNum];
		int[][] totalNetworkLoad = new int [topoNum][requestsNum];
		int[][] totalServerLoad = new int [topoNum][requestsNum];
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				TimeLine.clear();
				Statistic.init(contentNum);
				Logger.setFile("topo" + String.valueOf(i) + "-req" + String.valueOf(j) + ".txt");
				topo[i].setTimeLine(r[j]);
				
				TimeLine.execute();
				Statistic.display();
				
				totalReq[i][j] = Statistic.totalRequest;
				totalHit[i][j] = Statistic.totalCacheHit;
				totalMiss[i][j] = Statistic.totalCacheMiss;
				totalNetworkLoad[i][j] = Statistic.totalNetworkLoad;
				totalServerLoad[i][j] = Statistic.totalServerLoad;
			}
			
		}
		System.out.println("reqs.size");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(r[j].reqs.size());
				System.out.print("\t");
			}
			System.out.println();
		}
		/*
		System.out.println("totalRequest");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(totalReq[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}*/
		System.out.println("ServerLoads");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(totalServerLoad[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("totalCacheHit");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(totalHit[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("totalMiss");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(totalMiss[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("NetworkLoads");
		for(int i = 0; i < topoNum; i++)
		{
			for(int j = 0; j < requestsNum; j++)
			{
				System.out.print(totalNetworkLoad[i][j]);
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
