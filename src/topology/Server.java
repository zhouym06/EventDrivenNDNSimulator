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

	private void genData()				//�������ɷֲ�����������
	{
		int[] cSize = new int[contentNum];	//˳�����е�
		
		
	}

}
