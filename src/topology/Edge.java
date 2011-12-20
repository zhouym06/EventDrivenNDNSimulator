package topology;

public class Edge {
	Router r1;
	Router r2;
	double delay;
	public Edge(Router r1, Router r2)
	{
		this(r1, r2, 0);
	}
	public Edge(Router r1, Router r2, double delay)
	{
		this.r1 = r1;
		this.r2 = r2;
		this.delay = delay;
	}
}
