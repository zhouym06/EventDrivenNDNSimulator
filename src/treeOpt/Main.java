package treeOpt;

public class Main {
	public static void main(String[] args) {
		//int D = 5;
		//int H = 10;
		
		int d = 2;
		int h = 5;
		int c = 2000;
		int n = 10000;
		int cacheSizes[] = new int[h];
		
		cacheSizes = new HillOptimiser(d,h,c,n).optimise();
		for(int cz:cacheSizes)
		{
			System.out.print(cz);
			System.out.print("\t");
		}
		System.out.println();
		
		cacheSizes = new GAOptimiser(d,h,c,n).optimise();
		for(int cz:cacheSizes)
		{
			System.out.print(cz);
			System.out.print("\t");
		}
		System.out.println();
		cacheSizes = new AnnealOptimiser(d,h,c,n).optimise();
		for(int cz:cacheSizes)
		{
			System.out.print(cz);
			System.out.print("\t");
		}
			

	}

}
