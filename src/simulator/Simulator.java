package simulator;

import logger.Logger;
import event.RequestGenerator;
import event.Requests;
import event.TimeLine;
import statistic.Statistic;
import topology.Topology;
import util.Tree;

public class Simulator {
	public static void main(String[] args) {
		/**/
		//Logger.setFile("log.txt");
		//getDistributeByLevelInLine();
		//getDistributeByLevelInTree();
		//getOptInTree();
		proveHillDiff();
		//showReplaceDiff();
		//get1();
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
	
	private static void showReplaceDiff() {
		
	}

	private static void getDistributeByLevelInLine() {
		Logger.clear();
		Logger.setFile("distrLine.txt");
		Logger.log("getDistributeByLevelInLine", Logger.INFO);
		
		int contentNum = 100;
		int routerNum = 10;
		int requestNum = 1000;
		int requestsNum = 1;
		int totalRequestTime = 20000;
		
		Statistic.init(contentNum, routerNum);//contentNum
		
		Logger.log("generate Topology", Logger.INFO);
		
		int serverNum = 1;
		int[] cacheSizes = new int[routerNum];
		int cacheGranularity = 10;
		int[][] totalNetworkLoad = new int [routerNum][cacheGranularity];
		for(int l = 0; l < cacheGranularity; l += 1)
		{
			for(int m = 0; m < routerNum; m++)
			{
				/**/
				// 集中
				for(int n = 0; n < routerNum; n++)
				{
					cacheSizes[n] = 0;
				}
				cacheSizes[m] = (int) (contentNum * ( ((double)(l + 1.0)) / (double) cacheGranularity));
				System.out.println("cacheSizes[m]:" + cacheSizes[m]);
				
				/* 
				//分散
				for(int n = 0; n < routerNum; n++)
				{
					cacheSizes[n] = 5;
					//cacheSizes[n] = (int) (contentNum * ( ((double)(l + 1.0)) / (double) cacheGranularity) / routerNum);
					//cacheSizes[n] = (int) (contentNum * ( ((double)(l + 1.0)) / (double) cacheGranularity));
				}
				*/
				System.out.println("cacheSizes[0]:" + cacheSizes[0]);
				System.out.println("cacheSizes[9]:" + cacheSizes[9]);
				Logger.setFile("topo" + 0 + "-percent" + l + ".txt");
				//Logger.setFile("topo" + 0 + "-percent" + l + "-cacheAt" + m + ".txt");
				Topology topo = Topology.getLineTopology(routerNum, serverNum, contentNum, cacheSizes);
				//topo.displayTopology();
				//topo.displayFIB();
				
				Logger.log("generate Requests", Logger.INFO);
				
				Requests r[] = new Requests[requestsNum];
				
				for(int i = 0; i < requestsNum; i++)
				{
					r[i] = RequestGenerator.getPoissonRequests(requestNum, contentNum, totalRequestTime);
					//r[i] = RequestGenerator.genOnOffRequests(requestNum, contentNum, totalRequestTime);
				}
				int[][] interestCount = new int [routerNum][contentNum];
				for(int i = 0; i < requestsNum; i++)
				{
					TimeLine.clear();
					Statistic.init(contentNum, routerNum);
					
					topo.setTimeLine(r[i]);			//init TimeLine
					TimeLine.execute();
					Statistic.display();
					totalNetworkLoad[m][l] = Statistic.totalNetworkLoad;
					//totalNetworkLoad[0][l] = Statistic.totalNetworkLoad;
					
					interestCount = Statistic.contentLeveledReq;
					System.out.println("request" + i);
					for(int j = 0; j < routerNum; j++)
					{
						System.out.print("reqCount @router" + j + "\t");
						for(int k = 0; k < contentNum;k++)
						{
							System.out.print(interestCount[j][k] + "\t");
						}
						System.out.println();
					}
					System.out.println();
					/**/
				}
			}
		}
		System.out.println("totalNetworkLoad[routerNum][cacheGranularity]:");
		for(int l = cacheGranularity - 1; l >= 0; l -= 1)
		{
			for(int m = 0; m < routerNum; m++)
			{
				System.out.print(totalNetworkLoad[m][l] + "\t");
			}
			System.out.println();
		}
		
	}
	private static void getDistributeByLevelInTree() {
		Logger.clear();
		Logger.setFile("distrTree.txt");
		Logger.log("getDistributeByLevelInTree", Logger.INFO);
		
		int contentNum = 100;
		int treeLevel = 10;
		int treeDegree = 3;
		
		int requestsNum = 1;
		int requestNum = 1000;		
		int totalRequestTime = 20000;

		int routerNum = Tree.getTreeNodeNum(treeDegree, treeLevel);
		int serverNum = 1;
		
		System.out.println("routerNum: " + routerNum);
		
		Statistic.init(contentNum, routerNum);//contentNum
		
		Logger.log("generate Topology", Logger.INFO);
		int[] cacheSizes = new int[treeLevel];
		
		int cacheGranularity = 10;
		int[][] totalNetworkLoad = new int [treeLevel][cacheGranularity];
		for(int l = 0; l < cacheGranularity; l += 1)
		{
			for(int m = 0; m < treeLevel; m++)
			{
				//集中
				for(int n = 0; n < treeLevel; n++)
				{
					cacheSizes[n] = 0;
				}
				cacheSizes[m] = (int) Math.ceil((contentNum * 
						( ((double)(l + 1.0)) / (double) cacheGranularity) / Tree.getTreeLevelSize(treeDegree, m)));
				//		((double)(l + 1.0) / (double) cacheGranularity) );
				/*
				//分散
				for(int n = 0; n < routerNum; n++)
				{
					cacheSizes[m][n] = (int) (contentNum * ( ((double)(l + 1.0)) / (double) cacheGranularity) / 10);
				}
				*/
				System.out.println("cacheSizes[m]:" + cacheSizes[m]);
				
				Logger.setFile("topo" + 0 + "-percent" + l + "-cacheAt" + m + ".txt");
				Topology topo = Topology.getTreeTopology(treeLevel, treeDegree, serverNum, contentNum, cacheSizes);
				//topo.displayTopology();
				//topo.displayFIB();
				
				Logger.log("generate Requests", Logger.INFO);
				
				Requests r[] = new Requests[requestsNum];
				for(int i = 0; i < requestsNum; i++)
				{
					r[i] = RequestGenerator.getPoissonRequests(requestNum, contentNum, totalRequestTime);
					//r[i] = RequestGenerator.genOnOffRequests(requestNum, contentNum, totalRequestTime);
				}
				//int[][] interestCount = new int [routerNum][contentNum];
				for(int i = 0; i < requestsNum; i++)
				{
					TimeLine.clear();
					Statistic.init(contentNum, routerNum);
					
					topo.setTimeLine(r[i]);			//init TimeLine
					TimeLine.execute();
					Statistic.display();
					totalNetworkLoad[m][l] = Statistic.totalNetworkLoad;
					/*
					interestCount = Statistic.contentLeveledReq;
					System.out.println("request" + i);
					for(int j = 0; j < routerNum; j++)
					{
						System.out.println("reqCount @router" + j);
						for(int k = 0; k < contentNum;k++)
						{
							System.out.print(interestCount[j][k] + "\t");
						}
						System.out.println();
					}
					System.out.println();
					*/
				}
			}
		}
		System.out.println("totalNetworkLoad[routerNum][cacheGranularity]:");
		for(int l = cacheGranularity - 1; l >= 0; l -= 1)
		{
			for(int m = 0; m < treeLevel; m++)
			{
				System.out.print(totalNetworkLoad[m][l] + "\t");
			}
			System.out.println();
		}
		
	}
	private static void proveHillDiff() {
		
		for(int hillNo = 0; hillNo < 4; hillNo++)
		{
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(hillNo);
			System.out.println();
			
			int totalCache[] = new int[4];
			totalCache[0] = 800;
			totalCache[1] = 800;
			totalCache[2] = 2000;
			totalCache[3] = 2000;
			
			int degrees[] = new int[4];
			degrees[0] = 5;
			degrees[1] = 2;
			degrees[2] = 5;
			degrees[3] = 2;
			int treeDegree = degrees[hillNo];
			int contentNum = 10000;
			
			int treeLevel = 5;
			int serverNum = 1;
			int requestsNum = 1;
			int requestNum = 10000;		
			int totalRequestTime = 20000;
			int totalNetworkLoad[] = new int [treeLevel];
			
			
			int routerNum = Tree.getTreeNodeNum(treeDegree, treeLevel);
			//serverNum = 20;
			System.out.println("routerNum: " + routerNum + "\tfor treeDegree:" + treeDegree);
			Statistic.init(contentNum, routerNum);//contentNum
			for(int m = 0; m < treeLevel; m++)
			{
				int cacheSizes[] = new int[treeLevel];
				for(int i = 0; i < treeLevel; i++)
				{
					cacheSizes[i] = 0;
				}
				cacheSizes[m] = (int) Math.ceil(( (double)totalCache[hillNo] / Tree.getTreeLevelSize(treeDegree, m)));
				System.out.println("cacheSizes[" + m + "]:" + cacheSizes[m]);
				Logger.setFile("topo" + 0 + "-cacheAt" + m + ".txt");
				Topology topo = Topology.getTreeTopology(treeLevel, treeDegree, serverNum, contentNum, cacheSizes);
				Logger.log("Generating Requests", Logger.INFO);
				Requests r[] = new Requests[requestsNum];
				for(int i = 0; i < requestsNum; i++)
				{
					r[i] = RequestGenerator.getPoissonRequests(requestNum, contentNum, totalRequestTime);
					//r[i] = RequestGenerator.genOnOffRequests(requestNum, contentNum, totalRequestTime);
				}
				for(int i = 0; i < requestsNum; i++)
				{
					TimeLine.clear();
					Statistic.init(contentNum, routerNum);
					topo.setTimeLine(r[i]);			//init TimeLine
					TimeLine.execute();
					//Statistic.display();
					totalNetworkLoad[m] = Statistic.totalNetworkLoad;
				}
			}
			int minIndex = -1;
			int minValue = Integer.MAX_VALUE;
			for(int m = 0; m < treeLevel; m++)
			{
				if(totalNetworkLoad[m] < minValue)
				{
					minValue = totalNetworkLoad[m];
					minIndex = m;
				}
			}
			Logger.log("minIndex" + minIndex + "\t Load:" + minValue + "\n", Logger.INFO);
			
			
			
			
			
			
			Statistic.init(contentNum, routerNum);//contentNum
			int hillCacheSizes[][] = new int[4][treeLevel];
			
			//0
			hillCacheSizes[0][1] = (int) Math.ceil(( (double)403 / Tree.getTreeLevelSize(treeDegree, 1)));
			hillCacheSizes[0][2] = (int) Math.ceil(( (double)397 / Tree.getTreeLevelSize(treeDegree, 2)));
			//1
			hillCacheSizes[1][2] = (int) Math.ceil(( (double)192 / Tree.getTreeLevelSize(treeDegree, 2)));
			hillCacheSizes[1][4] = (int) Math.ceil(( (double)608 / Tree.getTreeLevelSize(treeDegree, 4)));
			//2
			hillCacheSizes[2][1] = (int) Math.ceil(( (double)820 / Tree.getTreeLevelSize(treeDegree, 1)));
			hillCacheSizes[2][2] = (int) Math.ceil(( (double)894 / Tree.getTreeLevelSize(treeDegree, 2)));
			hillCacheSizes[2][3] = (int) Math.ceil(( (double)286 / Tree.getTreeLevelSize(treeDegree, 3)));
			//3
			hillCacheSizes[3][2] = (int) Math.ceil(( (double)550 / Tree.getTreeLevelSize(treeDegree, 2)));
			hillCacheSizes[3][4] = (int) Math.ceil(( (double)1450 / Tree.getTreeLevelSize(treeDegree, 4)));
			
			
			
			Logger.setFile("hill" + hillNo + "" + ".txt");
			Topology topo = Topology.getTreeTopology(treeLevel, treeDegree, serverNum, contentNum, hillCacheSizes[hillNo]);
			Logger.log("Generating Requests", Logger.INFO);
			Requests r[] = new Requests[requestsNum];
			for(int i = 0; i < requestsNum; i++)
			{
				r[i] = RequestGenerator.getPoissonRequests(requestNum, contentNum, totalRequestTime);
				//r[i] = RequestGenerator.genOnOffRequests(requestNum, contentNum, totalRequestTime);
			}
			for(int i = 0; i < requestsNum; i++)
			{
				TimeLine.clear();
				Statistic.init(contentNum, routerNum);
				topo.setTimeLine(r[i]);			//init TimeLine
				TimeLine.execute();
				Statistic.display();
				int load = Statistic.totalNetworkLoad;
				Logger.log("Load:" + load, Logger.INFO);
			}
			System.out.println();
			System.out.println();
		}
		
		
	}
	private static void getOptInTree()
	{
		int contentNum = 1000;
		int treeLevel = 5;
		
		int serverNum = 1;
		int requestsNum = 1;
		int requestNum = 10000;		
		int totalRequestTime = 20000;
		int maxDegree = 8;
		int opt[] = new int[10];
		int totalNetworkLoad[][] = new int [maxDegree][treeLevel];
		for(int treeDegree = 2; treeDegree < maxDegree; treeDegree ++)
		{

			int routerNum = Tree.getTreeNodeNum(treeDegree, treeLevel);
			//
			serverNum = 20;
			System.out.println("routerNum: " + routerNum + "\tfor treeDegree:" + treeDegree);
			Statistic.init(contentNum, routerNum);//contentNum
			
			for(int m = 0; m < treeLevel; m++)
			{
				int cacheSizes[] = new int[treeLevel];
				for(int n = 0; n < treeLevel; n++)
				{
					cacheSizes[n] = 0;
				}
				cacheSizes[m] = (int) Math.ceil((contentNum * 
						(double)1.0 / Tree.getTreeLevelSize(treeDegree, m)));
				System.out.println("cacheSizes[m]:" + cacheSizes[m]);
				//		((double)1.0) );
				Logger.setFile("topo" + 0 + "-cacheAt" + m + ".txt");
				Topology topo = Topology.getTreeTopology(treeLevel, treeDegree, serverNum, contentNum, cacheSizes);
				Logger.log("Generating Requests", Logger.INFO);
				Requests r[] = new Requests[requestsNum];
				for(int i = 0; i < requestsNum; i++)
				{
					r[i] = RequestGenerator.getPoissonRequests(requestNum, contentNum, totalRequestTime);
					//r[i] = RequestGenerator.genOnOffRequests(requestNum, contentNum, totalRequestTime);
				}
				for(int i = 0; i < requestsNum; i++)
				{
					TimeLine.clear();
					Statistic.init(contentNum, routerNum);
					topo.setTimeLine(r[i]);			//init TimeLine
					TimeLine.execute();
					Statistic.display();
					totalNetworkLoad[treeDegree][m] = Statistic.totalNetworkLoad;
				}
			}
			int minIndex = -1;
			int minValue = Integer.MAX_VALUE;
			for(int m = 0; m < treeLevel; m++)
			{
				if(totalNetworkLoad[treeDegree][m] < minValue)
				{
					minValue = totalNetworkLoad[treeDegree][m];
					minIndex = m;
				}
			}
			opt[treeDegree] = minIndex;
		}
		System.out.println("opt[treeDegree]:");
		for(int i = 2; i < maxDegree; i++)
		{
			System.out.print("\t" + opt[i]);
		}
		System.out.println();
		System.out.println("totalNetworkLoad:");
		for(int i = 2; i < maxDegree; i++)
		{
			for(int j = 0; j < treeLevel; j++)
			{
				System.out.print("\t" + totalNetworkLoad[i][j]);
			}
			System.out.println();
		}
	}
	
/*
	private static void get1() {
		Logger.clear();
		//Logger.log("generate Topology", Logger.INFO);
		//Topology topo = new Topology("path");
		
		//topo[0] = Topology.getDefaultTopology1();
		int contentNum = 1000;
		int requestsNum = 20;
		int topoNum = 99;
		Topology topo[] = new Topology[topoNum];
		Statistic.init(contentNum);//contentNum
		int[][] cacheSizes = new int[topoNum][3];
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
	}
*/
}
