package treeOpt;

public class LoadModel {
	static int dimesion;
	static int height;
	static int contentNum;
	static double p[][];
	public static void initP(int n)
	{
		//init p[0] with Zipf
		double H = 0;
		for(int i = 0; i < n; i ++)
		{
			H += 1 / (double) (i + 1);
		}
		for(int i = 0; i < n; i ++)
		{
			p[0][i] = 1 / ((double) (i + 1)  * H);
		}
	}
	public static void init(int d, int h, int c)
	{
		dimesion = d;
		height = h;
		contentNum = c;
		p = new double[height][contentNum];
		initP(contentNum);
	}
	public static double getLoad(int[] cacheSize){
		double load = 0;
		for(int i = 1; i < height; i++)
		{
			for(int j = 0; j < contentNum; j++)
			{
				p[i][j] = p[i-1][j] * Math.pow(1 - p[i-1][j], (double)cacheSize[height - i] / (double)getCacheNum(dimesion, height - i));
				//p0���ӣ��Ӳ��ӷ�������1����client���ײ�router���㣬�Ӷ���router��serverҲ����
				load += p[i][j];
			}
		}
		return load;
	}
	private static int getCacheNum(int d, int h) {
		return (int)Math.pow(d, h);
		//return 1; for linear
	}

}
