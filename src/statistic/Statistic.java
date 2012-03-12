package statistic;

import logger.Logger;

public class Statistic {
	static int totalInterest;
	//InterestGeneration
	public static int[]routerInterestCount;
	
	public static int[] cacheHitCount;
	public static int[] cacheMissCount;
	public static int totalCacheHit;
	public static int totalCacheMiss;
	public Statistic(int CountentNum)
	{
		totalCacheHit = 0;
		totalCacheMiss = 0;
	}
	public static void countHit(int contentNo)
	{
		totalCacheHit++;
	}
	public static void countMiss(int contentNo)
	{
		totalCacheMiss++;
	}
	public static void display()
	{
		Logger.log("totalCacheMiss:" + (double)totalCacheMiss / (double)(totalCacheMiss + totalCacheHit) + " ," + totalCacheMiss, Logger.INFO);
		Logger.log("totalCacheHit:" + (double)totalCacheHit / (double)(totalCacheMiss + totalCacheHit) + " ," + totalCacheHit, Logger.INFO);
		
		Logger.log("", Logger.INFO);
		Logger.log("", Logger.INFO);
		Logger.log("", Logger.INFO);
	}
	public static void clear()
	{
		totalInterest = 0;
	}
	

}
