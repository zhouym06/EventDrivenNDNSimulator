package treeOpt;
import java.util.Random;

public class HillOptimiser extends Optimiser {
	public HillOptimiser(int d, int h, int c, int n) {
		super(d, h, c, n);
	}
	public int[] optimise() {
		int cacheSizes[] = new int[height];
		cacheSizes[0] = totalCacheSize;
		int sum = 0;
		for(int i = 0; sum < totalCacheSize; i++)
		{
			int random = (int)(Math.random() * totalCacheSize * 2 / height);
			if(sum + random > totalCacheSize || i == height - 1)
			{
				cacheSizes[i] = totalCacheSize - sum;
				sum += cacheSizes[i];
			}else{
				cacheSizes[i] = random;
				sum += random;
			}
		}
		return optimise(cacheSizes);
	}
	public int[] optimise(int cacheSizes[], int seedNum) {
		
		return cacheSizes;
		
	}
	public int[] optimise(int cacheSizes[]) {
		for (int i = 0; i < height; i++) {
			System.out.print(cacheSizes[i] + "\t");
		}
		System.out.println();
		
		LoadModel.init(dimesion, height, contentNum);
		double currentLoad = LoadModel.getLoad(cacheSizes);

		boolean goOnflag = true;
		int count = 0;
		while (goOnflag) {
			count ++;
			goOnflag = false;
			double minNeighborLoad = currentLoad;
			int minNeighborCaches[] = new int[height];
			for (int i = 0; i < height; i++) {
				if (cacheSizes[i] - 1 < 0)
					continue;
				for (int j = 0; j < height; j++) {
					if (i == j)
						continue;
					int testNeighborCaches[] = new int[height];
					double testLoad;
					for (int t = 0; t < height; t++) {
						testNeighborCaches[t] = cacheSizes[t];
					}
					testNeighborCaches[i] = cacheSizes[i] - 1;
					testNeighborCaches[j] = cacheSizes[j] + 1;
					testLoad = LoadModel.getLoad(testNeighborCaches);
					//System.out.println(i + "," + j + ":" + testLoad + "," + currentLoad);
					if (testLoad < minNeighborLoad) {
						minNeighborLoad = testLoad;
						for (int t = 0; t < height; t++) {
							minNeighborCaches[t] = testNeighborCaches[t];
						}
					}

				}
			}
			if (minNeighborLoad < currentLoad) {
				//if(count % 10 == 0)
				{
					//System.out.println(minNeighborLoad + "," + currentLoad);
				}
				
				currentLoad = minNeighborLoad;
				for (int t = 0; t < height; t++) {
					cacheSizes[t] = minNeighborCaches[t];
				}
				goOnflag = true;
			}
			if(count % 50 == 0)
			{
				//System.out.println(count);
			}
		}
		return cacheSizes;
	}

}
