package topology;


public class Server extends Router{
	String servername;
	Router linkedTo;
	int[] contentSize;					//sorted by popularity
	int contentNum;						
	public Server(int contentNum) {
		this.contentNum = contentNum;
	}
	public void offer()
	{
		
	}

	private void genData()				//生成幂律分布的内容数据
	{
		int[] cSize = new int[contentNum];	//顺序排列的
		
		
	}

}
