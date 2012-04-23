package util;

public class Tree {
	public static int getTreeLevel(int treeDegree,int routerID)
	{
		int sum = 0;
		int levelNum = 1;
		int level;
		for(level = 1; routerID < sum; level++)
		{
			levelNum *= treeDegree;
			sum += levelNum;
		}
		return level;
	}
	public static int getParent(int treeDegree,int treeLevel, int routerID)
	{
		int parent = (int)Math.floor((routerID - 0.1)/treeDegree);
		return parent;
	}
}
