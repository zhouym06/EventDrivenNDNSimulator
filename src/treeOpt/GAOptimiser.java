package treeOpt;

import java.util.Random;

public class GAOptimiser extends Optimiser{
	int G = 500;			//generation
	int P = 100;			//population size
	double pc = 0.6;		//probability of performing crossover
	double pm = 0.2;		//probability of mutation
	int cacheSizes[][];
	double scores[];
	
	int best[];
	double bestScore = 0;
	
	Random r;
	
	
	public GAOptimiser(int d, int h, int c, int n) {
		super(d, h, c, n);
		cacheSizes = new int[P][height];
		best = new int[height];
		scores = new double[P];
		r = new Random();
	}
	
	
	public int[] optimise() {
		LoadModel.init(dimesion, height, contentNum);
		randomInit();
		for(int count = 0; count < G; count++)
		{
			if(count % 50 == 0)
			{
				//System.out.println(bestScore);
			}
			cacheSizes[0] = best;
			
			for(int i = 0; i < P; i++)
			{
				if(r.nextDouble() < pc)	//crossover
				{
					int other = r.nextInt(height);
					cacheSizes[i] = crossover(cacheSizes[i], cacheSizes[other], i);
					
				}
				if(r.nextDouble() < pm)
				{
					cacheSizes[i] = mutate(cacheSizes[i]);
				}
			}
		}
		System.out.println("Best:" + bestScore);
		System.out.println("Load:" + LoadModel.getLoad(best));
		return best;
	}
	private double getScore(int index)
	{
		return ((height - LoadModel.getLoad(cacheSizes[index])) );
		//return LoadModel.getLoad(cacheSizes[index]);
	}
	
	private double getScore(int[] cacheS)
	{
		return ((height - LoadModel.getLoad(cacheS)) );
		//return LoadModel.getLoad(cacheS);
	}
	public int[] mutate(int[] o)
	{
		int result[] = new int[height];
		if(r.nextDouble() < 0.3)
		{
			result = mutate1(o);
		}
		else
			result = mutate2(o);
		double score = getScore(result);
		if(score > bestScore)
		{
			bestScore = score;
			best = result;
		}
		return result;
		
	}
	public int[] mutate1(int[] o)
	{
		int result[] = o;
		int i = r.nextInt(height);
		int j = r.nextInt(height);
		int temp = result[i];
		result[i] = result[j];
		result[j] = temp;
		return result;
	}
	public int[] mutate2(int[] o)
	{
		int result[] = o;
		int i = r.nextInt(height);
		int j = r.nextInt(height);
		if(result[i] > 0)
		{
			result[i]--;
			result[j]++;
		}
		else if(result[j] > 0)
		{
			result[j]--;
			result[i]++;
		}
		return result;
	}
	public int[] crossover(int[] a, int[]b, int index)
	{
	
		int result[] = new int[height];
		if(r.nextDouble() < 0.4)
		{
			result = crossover1(a,b);
		}
		else
		{
			if(r.nextDouble() < 0.8)
				result = mutate1(crossover2(a,b));
			else
				result = crossover2(a,b);
		}
			
		
		double score = getScore(result);
		if(score > scores[index])
		{
			if(score > bestScore)
			{
				bestScore = score;
				best = result;
			}
			return result;
		}
		if(r.nextDouble() < 0.4)
			return result;
		return a;
	}
	public int[] crossover1(int[] a, int[]b)	//average
	{
		int result[] = new int[height];
		int left = 0;
		for(int i = 0; i < height; i++)
		{
			result[i] = a[i] + b[i];
			if(result[i] % 2 == 0)
				result[i] /= 2;
			else
			{
				if(left == 0)
				{
					result[i] = (int)Math.floor((double)result[i] / 2);
					left = 1;
				}
				else if(left == 1)
				{
					result[i] = (int)Math.ceil((double)result[i] / 2);
					left = 0;
				}else
				{
					System.out.println("Error!");
				}
			}
		}
		return result;
	}
	public int[] crossover2(int[] a, int[]b)	//remove totalCacheSize
	{
		int result[] = new int[height];
		for(int i = 0; i < height; i++)
		{
			result[i] = a[i] + b[i];
		}
		int removedC = 0;
		while(removedC < totalCacheSize)
		{
			int i = 0;
			while(result[i] == 0){
				i++;
			}
			
			if(result[i] < totalCacheSize - removedC)
			{
				removedC += result[i];
				result[i] = 0;
			}else{
				result[i] -= totalCacheSize - removedC;
				removedC = totalCacheSize;
			}
		}
		return result;
	}
	private void randomInit() {
		for(int j = 0; j < P; j++)
		{
			int sum = 0;
			for(int i = 0; sum < totalCacheSize; i++)
			{
				int random = (int)(r.nextDouble() * totalCacheSize * 2 / height);
				if(sum + random > totalCacheSize || i == height - 1)
				{
					cacheSizes[j][i] = totalCacheSize - sum;
					sum += cacheSizes[j][i];
				}else{
					cacheSizes[j][i] = random;
					sum += random;
				}
			}
			scores[j] = getScore(j);
			if(scores[j] > bestScore)
			{
				bestScore = scores[j];
				best = cacheSizes[j];
			}
			
			
		}
	}

}
