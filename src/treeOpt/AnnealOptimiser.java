package treeOpt;

import java.util.Random;

public class AnnealOptimiser extends Optimiser{
	
	Random r;

	

	public AnnealOptimiser(int d, int h, int c, int n) {
		super(d, h, c, n);
		r = new Random();
	}

	public int[] optimise() {
		LoadModel.init(dimesion, height, contentNum);
		
		
		int cacheSizes[] = randomInitCache();
		double currentLoad = LoadModel.getLoad(cacheSizes);
		
		double minLoad = currentLoad;
		int min[] = cacheSizes;
		
		double T;
		int unchanged = 0;
		int count = 0;
		//for(T = 50; (T > 0.1) && (unchanged < totalCacheSize); T *= 0.92)
		for(T = 50; (T > 0.1); T *= 0.92)
		{
			System.out.println(T + "," + unchanged);
			for(int i = 0; i < totalCacheSize / 10; i++)
			{
				int[] nL = randomNeighbour(cacheSizes);
				double neighbourLoad = LoadModel.getLoad(nL);
				if(neighbourLoad < currentLoad)
				{
					currentLoad = neighbourLoad;
					cacheSizes = nL;
					if(neighbourLoad < minLoad)
					{
						minLoad = neighbourLoad;
						min = nL;
					}
					unchanged = 0;
				}
				else		//(neighbourLoad > currentLoad)
				{
					double p = Math.exp((currentLoad - neighbourLoad) * 10000  / T );
					/*
					count++;
					if(count%100 == 0)
					{
						System.out.print(T);
						System.out.print("\t");
						System.out.print((currentLoad - neighbourLoad) * 10000 / T);
						System.out.print("\t");
						System.out.print(Math.exp((currentLoad - neighbourLoad) * 10000));
						System.out.print("\t");
						System.out.println(p);
						
					}*/
					
					if(r.nextDouble() < p)
					{
						unchanged = 0;
						currentLoad = neighbourLoad;
						cacheSizes = nL;
					}
				}
				unchanged++;		
			}
		}
		System.out.println(T + "," + unchanged);
		return min;
	}
	private int[] randomNeighbour(int[] cacheSizes)
	{
		int[] result = cacheSizes;
		int i;
		do
		{
			i = r.nextInt(height);
		}while(result[i] == 0);
		
		int j;
		do
		{
			j = r.nextInt(height);
		}while(j == i);
		
		result[i]--;
		result[j]++;
		return result;
	}

	private int[] randomInitCache() {
		int cacheSizes[] = new int[height];
		int sum = 0;
		for(int i = 0; sum < totalCacheSize; i++)
		{
			int random = (int)(r.nextDouble() * totalCacheSize * 2 / height);
			if(sum + random > totalCacheSize || i == height - 1)
			{
				cacheSizes[i] = totalCacheSize - sum;
				sum += cacheSizes[i];
			}else{
				cacheSizes[i] = random;
				sum += random;
			}
		}
		return cacheSizes;
	}

}
