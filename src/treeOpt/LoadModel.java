package treeOpt;

public class LoadModel {
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
	public static double getLoad(int d, int h, int c, int n, int[] cacheSize){
		p = new double[h][n];
		double load = 0;
		initP(n);
		for(int i = 1; i < h; i++)
		{
			for(int j = 0; j < n; j++)
			{
				p[i][j] = p[i-1][j] * Math.pow(1 - p[i-1][j], (double)cacheSize[h - i] / (double)getCacheNum(d, h - i));
				//p0不加，加不加反正都是1，从client到底层router不算，从顶层router到server也不算
				load += p[i][j];
			}
		}
		//p0不加，加不加反正都是1，从client到底层router不算，从顶层router到server也不算
		/*
		for(int i = 1; i < h; i++)
		{
			for(int j = 0; j < n; j++)
			{
				load += p[i][j];
			}
		}*/
		/*
		System.out.println(d + "," + h + "," + c + "," + n);
		for(int i = 1; i < h; i++)
		{
			System.out.print(cacheSize[i] + "\t");
		}
		System.out.println();
		System.out.println("load:" + load);
		*/
		return load;
	}
	private static int getCacheNum(int d, int h) {
		return (int)Math.pow(d, h);
		//return 1; for linear
	}

}
