package util;

public class Tree {
	public static int getTreeLevel(int treeDegree,int routerID)
	{
		int sum = 0;
		int levelNum = 1;
		int level;
		for(level = 0; routerID > sum; level++)
		{
			levelNum *= treeDegree;
			sum += levelNum;
		}
		return level;
	}
	public static int getTreeNodeNum(int treeDegree,int treeLevel)
	{
		int sum = 0;
		int levelNum = 1;
		for(int i = 0; i < treeLevel;i++)
		{
			sum += levelNum;
			levelNum *= treeDegree;
		}
		return sum;
	}
	public static int getTreeLevelSize(int treeDegree,int treeLevel)
	{
		int levelSize = 1;
		for(int i = 0; i < treeLevel;i++)
		{
			levelSize *= treeDegree;
		}
		return levelSize;
	}
	public static int getParent(int treeDegree,int treeLevel, int routerID)
	{
		int parent = (int)Math.floor((routerID - 0.1)/treeDegree);
		return parent;
	}
}
