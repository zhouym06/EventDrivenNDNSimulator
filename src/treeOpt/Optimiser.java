package treeOpt;

public abstract class Optimiser {
	int dimesion = 0;
	int height = 0;
	int totalCacheSize = 0;
	int contentNum = 0;
	public Optimiser(int d, int h, int c, int n) {
		this.dimesion = d;
		this.height = h;
		this.totalCacheSize = c;
		this.contentNum = n;
	}
	public abstract int[] optimise();
}
