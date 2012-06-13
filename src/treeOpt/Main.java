package treeOpt;

public class Main {
	public static void main(String[] args) {
		//int D = 5;
		//int H = 10;
		
		int[] dd = new int[4];
		dd[0] = 5;
		dd[1] = 2;
		dd[2] = 5;
		dd[3] = 2;
		int[] hh = new int[4];
		hh[0] = 5;
		hh[1] = 5;
		hh[2] = 5;
		hh[3] = 5;
		int[] cc = new int[4];
		cc[0] = 800;
		cc[1] = 800;
		cc[2] = 2000;
		cc[3] = 2000;
		int[] nn = new int[4];
		nn[0] = 10000;
		nn[1] = 10000;
		nn[2] = 10000;
		nn[3] = 10000;
		
		
		for(int i = 0; i < 4; i++)
		{
			
			int d = dd[i];
			int h = hh[i];
			int c = cc[i];
			int n = nn[i];
			int cacheSizes[] = new int[h];
			
			System.out.println(i + "Hill:");
			cacheSizes = new HillOptimiser(d,h,c,n).optimise();
			for(int cz:cacheSizes)
			{
				System.out.print(cz);
				System.out.print("\t");
			}
			System.out.println();
			
			System.out.println(i + "GA:");
			cacheSizes = new GAOptimiser(d,h,c,n).optimise();
			for(int cz:cacheSizes)
			{
				System.out.print(cz);
				System.out.print("\t");
			}
			System.out.println();
			
			
			System.out.println(i + "GA + hill:");
			cacheSizes = new HillOptimiser(d,h,c,n).optimise(cacheSizes);
			for(int cz:cacheSizes)
			{
				System.out.print(cz);
				System.out.print("\t");
			}
			System.out.println();
			/**/
			/**/
			System.out.println(i + "An:");
			cacheSizes = new AnnealOptimiser(d,h,c,n).optimise();
			System.out.print("\t");
			for(int cz:cacheSizes)
			{
				System.out.print(cz);
				System.out.print("\t");
			}
			System.out.println();
			
			System.out.println(i + "An + hill:");
			cacheSizes = new HillOptimiser(d,h,c,n).optimise(cacheSizes);
			for(int cz:cacheSizes)
			{
				System.out.print(cz);
				System.out.print("\t");
			}
			System.out.println();
		}
		
			

	}

}
