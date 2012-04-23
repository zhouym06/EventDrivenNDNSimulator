package statistic;

import logger.Logger;

public class Statistic {
	//contentNo and serverNo should be in different dimension,but this will lead to route problem or request generation problem
	//leave it for request generate and keep route unchanged
	static int contentNum;
	public static int totalCacheHit;
	public static int totalCacheMiss;
	public static int totalRequest;
	public static int totalNetworkLoad;
	public static int totalServerLoad;
	public static int[] cacheHitCount;
	public static int[] cacheMissCount;
	public static int[] requestCount;
	public static int[] networkLoad;
	public static int[] serverLoad;
	
	
	public static void init(int cNum)
	{
		contentNum = cNum;
		totalCacheHit = 0;
		totalCacheMiss = 0;
		totalRequest = 0;
		totalNetworkLoad = 0;
		totalServerLoad = 0;
		cacheHitCount = new int [contentNum];
		cacheMissCount = new int [contentNum];
		requestCount = new int [contentNum];
		networkLoad = new int [contentNum];
		serverLoad = new int [contentNum];
	}
	public static void countRequest(int contentNo)
	{
		totalRequest++;
		requestCount[contentNo]++;
	}
	public static void countServerLoad(int contentNo)
	{
		totalServerLoad++;
		serverLoad[contentNo]++;
	}
	public static void countHit(int contentNo)
	{
		totalCacheHit++;
		cacheHitCount[contentNo]++;
	}
	public static void countMiss(int contentNo)
	{
		totalCacheMiss++;
		cacheMissCount[contentNo]++;
	}
	public static void addTTL(int contentNo, int ttl)
	{
		totalNetworkLoad += ttl;
		networkLoad[contentNo] += ttl;
	}
	
	
	public static void display()
	{
		Logger.log("totalCacheMiss:" + (double)totalCacheMiss / (double)(totalCacheMiss + totalCacheHit) + " ," + totalCacheMiss, Logger.INFO);
		Logger.log("totalCacheHit:" + (double)totalCacheHit / (double)(totalCacheMiss + totalCacheHit) + " ," + totalCacheHit, Logger.INFO);
		
		Logger.log("totalRequest:" + totalRequest, Logger.INFO);
		Logger.log("totalNetworkLoad:" + totalNetworkLoad, Logger.INFO);
		for(int i = 0;i<cacheHitCount.length;i++)
			Logger.log("Content-" + i + ": AllReq:" + requestCount[i] + ", hit:" + cacheHitCount[i] + ", miss:" + cacheMissCount[i] + ", networkLoad:" + networkLoad[i], Logger.INFO);
	}
	public static void clear()
	{
		contentNum = 1;
		totalCacheHit = 0;
		totalCacheMiss = 0;
		totalRequest = 0;
		totalNetworkLoad = 0;
		totalServerLoad = 0;
		cacheHitCount = new int [contentNum];
		cacheMissCount = new int [contentNum];
		requestCount = new int [contentNum];
		networkLoad = new int [contentNum];
		serverLoad = new int [contentNum];
	}
	

}
